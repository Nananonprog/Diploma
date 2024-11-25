package org.springproject.coffeeshopapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springproject.coffeeshopapp.entity.Category;
import org.springproject.coffeeshopapp.entity.Post;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;
import org.springproject.coffeeshopapp.service.ICategoryService;
import org.springproject.coffeeshopapp.service.IPostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IPostService postService;

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

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String email = principal.getName();
        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
        return "admin/profile";
    }

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddItem")
    public String loadAddItem(Model m) {
        List<Category> categories = categoryService.getAllCategory();
        m.addAttribute("categories", categories);
        return "admin/add_item";
    }

    @GetMapping("/category")
    public String category(Model m) {
        m.addAttribute("categories", categoryService.getAllCategory());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, HttpSession session) {
        Boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errorMsg", "Category Name already exists");
        }else{
            Category saveCategory = categoryService.saveCategory(category);
            if(saveCategory != null) {
                session.setAttribute("succMsg", "Category Saved");
            }else{
                session.setAttribute("errorMsg", "Category Saved Failed");
            }
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Integer id, HttpSession session) {
        Boolean deleteCategory = categoryService.deleteCategory(id);
        if (deleteCategory) {
            session.setAttribute("succMsg", "Category Deleted Successfully");
        }else{
            session.setAttribute("errorMsg", "Category Deleted Failed");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model m) {
        m.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, HttpSession session) {
        Category oldCategory = categoryService.getCategoryById(category.getId());
        if (oldCategory != null) {
            oldCategory.setName(category.getName());
        }
        Category updateCategory = categoryService.saveCategory(oldCategory);
        if (updateCategory != null) {
            session.setAttribute("succMsg", "Category Updated Successfully");
        }else
        {
            session.setAttribute("errorMsg", "Category Updated Failed");
        }
        return "redirect:/admin/category";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute Post post, HttpSession session, @RequestParam("file") MultipartFile image) throws IOException {
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
        post.setImage(imageName);

        Post savePost = postService.savePost(post);
        if (savePost != null) {
            String saveFile = new File("src/main/resources/static/images").getAbsolutePath();
            System.out.println(saveFile);
            if (!image.isEmpty()) {
                Path path = Paths.get(saveFile + File.separator + "icon" + File.separator + image.getOriginalFilename());
                System.out.println(path);

                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("succMsg", "Beverage  Saved Successfully");
        } else {
            session.setAttribute("errorMsg", "Beverage Saved Failed");        }
        return "redirect:/admin/loadAddItem";
    }

    @GetMapping("/items")
    public String loadViewPost(Model m,@RequestParam(defaultValue = "") String ch) {
        List<Post> posts = null;
        if(ch != null && ch.length()>0) {
            posts = postService.searchPost(ch);

        }else{
            posts = postService.getAllPost();
        }
        m.addAttribute("posts", posts);
        return "admin/items";
    }

    @GetMapping("deleteItem/{id}")
    public String deleteItem(@PathVariable Integer id, HttpSession session) {
        Boolean deletePost = postService.deletePost(id);
        if (deletePost) {
            session.setAttribute("succMsg", "Beverage Deleted Successfully");
        }else{
            session.setAttribute("errorMsg", "Beverage Deleted Failed");
        }
        return "redirect:/admin/items";
    }
    @GetMapping("/editItem/{id}")
    public String editItem(@PathVariable int id, Model m) {
        m.addAttribute("post", postService.getPostById(id));
        m.addAttribute("categories", categoryService.getAllCategory());
        return "admin/edit_items";
    }

}