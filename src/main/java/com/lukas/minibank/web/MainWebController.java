package com.lukas.minibank.web;

import com.lukas.minibank.business.service.CurrencyService;
import com.lukas.minibank.data.entity.AppUser;
import com.lukas.minibank.data.entity.Currency;
import com.lukas.minibank.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainWebController {
    private final CurrencyService currencyService;

    @Autowired
    public MainWebController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Home | Mini Bank");
        model.addAttribute("message", "Welcome to Mini Bank!");
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("title", "Admin Page | Mini Bank");

        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.userToString(loggedInUser);
        model.addAttribute("userInfo", userInfo);

        List<Currency> currenciesList = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currenciesList);

        return "adminPage";
     }
    @RequestMapping(value = "/admin/updateCurrencies", method = RequestMethod.GET)
    public ModelAndView updateCurrencies(Model model, Principal principal) {
        String status = null;
        boolean updateSuccessful = currencyService.updateCurrencies();

        if (updateSuccessful)   return new ModelAndView("redirect:/admin");
        else                    return new ModelAndView("redirect:/admin?error=true");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("title", "Login | Mini Bank");
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout Successful | Mini Bank");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        model.addAttribute("title", "User Info | Mini Bank");
        String userName = principal.getName();
        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.userToString(loggedInUser);
        model.addAttribute("userInfo", userInfo);
        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        model.addAttribute("title", "403 Forbidden | Mini Bank");
        if (principal != null) {
            User loggedInUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.userToString(loggedInUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hello " + principal.getName() + ", " //
                    + "<br> you do not have permission to access this page.";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

}