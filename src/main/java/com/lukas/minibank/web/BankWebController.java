package com.lukas.minibank.web;

import com.lukas.minibank.business.service.CurrentUser;
import com.lukas.minibank.business.service.BankService;
import com.lukas.minibank.business.service.CurrencyService;
import com.lukas.minibank.data.entity.AccountTransaction;
import com.lukas.minibank.data.entity.BankAccount;
import com.lukas.minibank.data.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class BankWebController {
    private final BankService bankService;
    private final CurrencyService currencyService;
    private final AccountTransactionRepository accountTransactionRepository;

    @Autowired
    public BankWebController(BankService bankService, CurrencyService currencyService, AccountTransactionRepository accountTransactionRepository) {
        this.bankService = bankService;
        this.currencyService = currencyService;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @RequestMapping(value = "/bankAccounts", method = RequestMethod.GET)
    public String bankAccounts(Model model, Principal principal) {
        model.addAttribute("title", "Bank Accounts | Mini Bank");

        CurrentUser loggedInUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        Long userId = loggedInUser.getUserId();

        List<BankAccount> bankAccounts = this.bankService.getBankAccountsByUserId(userId);
        System.out.println("baList:" + bankAccounts);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bankAccount", new BankAccount());
        return "bankAccounts";
    }

    @RequestMapping(value = "/accountTransactions", method = RequestMethod.POST)
    public String accountTransactions(@RequestParam(value="baId")Long baId, Model model, Principal principal) {
        model.addAttribute("title", "Transactions | Mini Bank");

        List<AccountTransaction> accountTransactions = this.bankService.getAccountTransactionsByBankAccountId(baId);
        Optional<BankAccount> selectedBankAccountOptional = this.bankService.getBankAccountsById(baId);
        BankAccount selectedBankAccount = Optional.ofNullable(selectedBankAccountOptional.get()).orElseThrow(IllegalArgumentException::new);

        System.out.println("atList:" + accountTransactions);
        model.addAttribute("accountTransactions", accountTransactions);
        model.addAttribute("selectedBankAccount", selectedBankAccount);
        return "accountTransactions";
    }

    @RequestMapping(value = { "/newTransfer" }, method = RequestMethod.GET)
    public String newTransaction(Model model, Principal principal) {
        model.addAttribute("title", "New Money Transfer | Mini Bank");
        AccountTransactionForm accountTransactionForm = new AccountTransactionForm();
        CurrentUser loggedInUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        Long userId = loggedInUser.getUserId();
        List<BankAccount> userBankAccounts = this.bankService.getBankAccountsByUserId(userId);
        List<BankAccount> allBankAccounts = this.bankService.getBankAccounts();

        model.addAttribute("accountTransactionForm", accountTransactionForm);
        model.addAttribute("userBankAccounts", userBankAccounts);
        model.addAttribute("allBankAccounts", allBankAccounts);
        return "newTransfer";
    }

    @RequestMapping(value = { "/newTransfer" }, method = RequestMethod.POST)
    public String newTransfer(Model model,
                             @ModelAttribute("accountTransactionForm") AccountTransactionForm accountTransactionForm) {

        AccountTransaction accountTransaction = new AccountTransaction();

        Optional<BankAccount> fromAccount = bankService.getBankAccountById( accountTransactionForm.getFromAccountId() );
        Optional<BankAccount> toAccount = bankService.getBankAccountById( accountTransactionForm.getToAccountId() );
        if (fromAccount.isPresent()) {
            accountTransaction.setFromAccount( fromAccount.get() );
        } else {
            model.addAttribute("errorMessage", "From Account could not be found.");
            return "redirect:newTransfer?error=true";
        }
        if (toAccount.isPresent()) {
            accountTransaction.setToAccount( toAccount.get() );
        } else {
            model.addAttribute("errorMessage", "To Account could not be found.");
            return "redirect:newTransfer?error=true";
        }

        BigDecimal sourceAmount = accountTransactionForm.getAmount();

        accountTransaction.setReason( accountTransactionForm.getReason() );
        accountTransaction.setSourceCurrency( fromAccount.get().getCurrency() );
        accountTransaction.setEndpointCurrency( toAccount.get().getCurrency() );
        accountTransaction.setTime(ZonedDateTime.now());
        accountTransaction.setSourceAmount( sourceAmount );

        if (currencyService.updateCurrencies()) {
            //Currency Rates Update successful
            BigDecimal outcomeAmount = currencyService.convert(fromAccount.get().getCurrency().getCode(), toAccount.get().getCurrency().getCode(), sourceAmount);
            accountTransaction.setEndpointAmount( outcomeAmount );

            bankService.addToBalance(accountTransaction.getFromAccount(), sourceAmount.multiply(new BigDecimal("-1")));
            bankService.addToBalance(accountTransaction.getToAccount(), outcomeAmount);
            accountTransactionRepository.save(accountTransaction);
            model.addAttribute("accountTransaction", accountTransaction);
            model.addAttribute("selectedBankAccount", accountTransaction.getFromAccount());
            model.addAttribute("accountTransactions", bankService.getAccountTransactionsByBankAccountId(accountTransaction.getFromAccount().getBaId()));

            return "transactionSuccess";
        } else {
            return "redirect:newTransfer?error=true";
        }

    }

    @RequestMapping(value = "/transactionSuccess", method = RequestMethod.GET)
    public String accountTransactions(@RequestParam(value="accountTransaction")AccountTransaction accountTransaction, Model model) {
        model.addAttribute("title", "Transaction Successful | Mini Bank");

        model.addAttribute("accountTransaction", accountTransaction);
        return "transactionSuccess";
    }


}
