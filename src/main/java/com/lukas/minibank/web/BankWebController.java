package com.lukas.minibank.web;

import com.lukas.minibank.business.bean.CurrentUser;
import com.lukas.minibank.business.service.BankService;
import com.lukas.minibank.data.entity.AccountTransaction;
import com.lukas.minibank.data.entity.BankAccount;
import com.lukas.minibank.data.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BankWebController {
    private final BankService bankService;
    private final AccountTransactionRepository accountTransactionRepository;

    @Autowired
    public BankWebController(BankService bankService, AccountTransactionRepository accountTransactionRepository) {
        this.bankService = bankService;
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
        AccountTransactionForm accountTransactionForm = new AccountTransactionForm();
        CurrentUser loggedInUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        Long userId = loggedInUser.getUserId();
        List<BankAccount> userBankAccounts = this.bankService.getBankAccountsByUserId(userId);

        model.addAttribute("accountTransactionForm", accountTransactionForm);
        model.addAttribute("userBankAccounts", userBankAccounts);
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
            return "/newTransfer?error=true";
        }
        if (toAccount.isPresent()) {
            accountTransaction.setToAccount( toAccount.get() );
        } else {
            model.addAttribute("errorMessage", "To Account could not be found.");
            return "/newTransfer?error=true";
        }

        accountTransaction.setReason( accountTransactionForm.getReason() );
        accountTransaction.setAmount( accountTransactionForm.getAmount() );
        accountTransaction.setSourceCurrency( fromAccount.get().getCurrency() );
        accountTransaction.setTime(ZonedDateTime.now());

        bankService.updateBalance(accountTransaction.getFromAccount(), (-1) * accountTransactionForm.getAmount());
        bankService.updateBalance(accountTransaction.getToAccount(), accountTransactionForm.getAmount());
        accountTransactionRepository.save(accountTransaction);
        model.addAttribute("selectedBankAccount", accountTransaction.getFromAccount());
        model.addAttribute("accountTransactions", bankService.getAccountTransactionsByBankAccountId(accountTransaction.getFromAccount().getBaId()));
        return "/accountTransactions";
    }


}
