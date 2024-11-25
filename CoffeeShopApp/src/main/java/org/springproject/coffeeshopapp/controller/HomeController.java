package org.springproject.coffeeshopapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springproject.coffeeshopapp.entity.Category;
import org.springproject.coffeeshopapp.entity.Post;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;
import org.springproject.coffeeshopapp.service.ICategoryService;
import org.springproject.coffeeshopapp.service.IPostService;
import org.springproject.coffeeshopapp.service.IUserService;
import org.springproject.coffeeshopapp.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IPostService postService;

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
    public String menu(Model m, @RequestParam(value = "category", defaultValue = "") String category, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name="pageSize", defaultValue = "4") Integer pageSize){
        List<Category> categories = categoryService.getAllCategory();
        m.addAttribute("categories", categories);
        m.addAttribute("paramValue", category);

//        List<Post> posts = postService.getAllSelectPosts(category);
//        m.addAttribute("posts", posts);
        Page<Post> page = postService.getAllPostPagination(pageNo, pageSize, category);
        List<Post> posts = page.getContent();
        m.addAttribute("posts", posts);
        m.addAttribute("postsSize", posts.size());
        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
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
    @GetMapping("/item/{id}")
    public String item(@PathVariable int id, Model m){
        Post postById = postService.getPostById(id);
        m.addAttribute("post", postById);
        return "view_item";
    }

    @GetMapping("/search")
    public String searchItem(@RequestParam String ch, Model m){
        List<Post> searchPosts = postService.searchPost(ch);
        m.addAttribute("posts", searchPosts);
        List<Category> categories = categoryService.getAllCategory();
        m.addAttribute("categories", categories);
        return "menu";
    }
}
