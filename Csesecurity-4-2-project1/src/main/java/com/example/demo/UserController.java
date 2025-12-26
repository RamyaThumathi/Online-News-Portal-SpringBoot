package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService; // Inject service to fetch news

    // User Registration Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        // FIX: Using the CamelCase name from the user's file structure
        return "userRegister"; 
    }

    // Save User
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/user/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/register";
        }
    }

    // Login Page
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
         if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        // FIX: Using the CamelCase name from the user's file structure
        return "userLogin"; 
    }

    // User Dashboard/Home Page - Displays only APPROVED news
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Fetch only news articles where status is "Approved"
        model.addAttribute("newsList", newsService.findNewsByStatus("Approved"));
        // FIX: Using the CamelCase name from the user's file structure
        return "userDashboard";
    }
}
