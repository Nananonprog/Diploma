package org.springproject.coffeeshopapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;
import org.springproject.coffeeshopapp.service.IUserService;
import org.springproject.coffeeshopapp.service.UserService;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepo userRepo;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepo.findByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/signin")
    public String login(){
        return "login";
    }


    @GetMapping("/user/home")
    public String home(){
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/menu")
    public String menu(){
        return "menu";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session){
//        System.out.println(user);
        User u = userService.saveUser(user);
        if(u != null){
//            System.out.println("User Saved Successfully");
            session.setAttribute("msg","Registration Success" );
        }else{
//            System.out.println("User Save Failed");
            session.setAttribute("msg","Registration Failed" );
        }
        return "redirect:/register";
    }
    @GetMapping("/item")
    public String item(){
        return "view_item";
    }
}
