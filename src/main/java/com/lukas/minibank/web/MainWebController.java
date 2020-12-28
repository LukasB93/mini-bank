package com.lukas.minibank.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class MainWebController {

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
        return "adminPage";
     }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
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