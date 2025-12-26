package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private NewsService newsService; 

    // --- Account Management ---

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("admin", new Admin());
        // Returns the unified registration view (register.html)
        return "register"; 
    }

    @PostMapping("/save")
    public String saveAdmin(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
        try {
            adminService.registerAdmin(admin);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            // FIX: Redirects to the unified /login page
            return "redirect:/login"; 
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Redirects back to the registration page using the GET mapping
            return "redirect:/admin/register";
        }
    }

    // --- Dashboard Routing ---

    @GetMapping("/dashboard")
    public String dashboard() {
        // Returns the main dashboard view, secured by ROLE_ADMIN
        return "admindashboard"; 
    }
    
    // --- News Review Workflow ---
   /* 
    @GetMapping("/review/pending")
    public String viewPendingNews(Model model) {
        // Fetch only news articles where status is "Pending"
        model.addAttribute("pendingNews", newsService.findNewsByStatus("Pending"));
        return "admin_review_pending"; 
    }
    */
    
    // NOTE: The @PostMapping for approval/rejection logic is required next.
}
