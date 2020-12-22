package com.lukas.minibank.web;

import com.lukas.minibank.business.service.UserService;
import com.lukas.minibank.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private final UserService userService;

    @Autowired
    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = this.userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
