package com.lukas.minibank.web;

import com.lukas.minibank.business.service.AppUserService;
import com.lukas.minibank.data.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/appUsers")
public class AppUserWebController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserWebController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<AppUser> appUsers = this.appUserService.getUsers();
        model.addAttribute("appUsers", appUsers);
        return "appUsers";
    }
}
