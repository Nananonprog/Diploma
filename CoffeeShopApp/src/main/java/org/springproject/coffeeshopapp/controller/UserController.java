package org.springproject.coffeeshopapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;

import java.security.Principal;
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model){
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
