package com.lukas.minibank.web;

import com.lukas.minibank.business.bean.CurrentUser;
import com.lukas.minibank.business.service.BankService;
import com.lukas.minibank.data.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class BankWebController {
    private final BankService bankService;

    @Autowired
    public BankWebController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping(value = "/bankAccounts", method = RequestMethod.GET)
    public String bankAccounts(Model model, Principal principal) {
        model.addAttribute("title", "Bank Accounts | Mini Bank");


        CurrentUser loggedInUser = (CurrentUser) ((Authentication) principal).getPrincipal();
        System.out.println(loggedInUser.getUserId());
        Long userId = loggedInUser.getUserId();

        List<BankAccount> bankAccounts = this.bankService.getBankAccountsByUserId(userId);
        System.out.println("baList:" + bankAccounts);
        model.addAttribute("bankAccounts", bankAccounts);
        return "bankAccounts";
    }


}
