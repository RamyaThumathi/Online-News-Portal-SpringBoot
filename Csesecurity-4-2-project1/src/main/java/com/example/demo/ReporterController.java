package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

@Controller
@RequestMapping("/reporter")
public class ReporterController {

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private NewsService newsService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("reporter", new Reporter());
        return "reporter_register";
    }

    @PostMapping("/save")
    public String saveReporter(@ModelAttribute Reporter reporter,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        try {
            if (!Files.exists(Paths.get(UPLOAD_DIR))) {
                Files.createDirectories(Paths.get(UPLOAD_DIR));
            }

            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String path = UPLOAD_DIR + "/" + fileName;
                file.transferTo(new File(path));
                reporter.setImage("/uploads/" + fileName);
            }

            reporterService.registerReporter(reporter);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login"; // unified login page
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reporter/register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "reporter_dashboard";
    }

    @GetMapping("/uploadNews")
    public String uploadNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "reporter_upload_news";
    }

    @PostMapping("/uploadNews")
    public String uploadNews(@ModelAttribute News news,
                             @RequestParam(value = "image1", required = false) MultipartFile image1,
                             @RequestParam(value = "image2", required = false) MultipartFile image2,
                             @RequestParam(value = "image3", required = false) MultipartFile image3,
                             RedirectAttributes redirectAttributes) throws IOException {

        Function<MultipartFile, String> saveFile = (file) -> {
            if (file == null || file.isEmpty()) return null;
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String path = UPLOAD_DIR + "/" + fileName;
                file.transferTo(new File(path));
                return "/uploads/" + fileName;
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
                return null;
            }
        };

        try {
            if (!Files.exists(Paths.get(UPLOAD_DIR))) {
                Files.createDirectories(Paths.get(UPLOAD_DIR));
            }

            news.setImage1(saveFile.apply(image1));
            news.setImage2(saveFile.apply(image2));
            news.setImage3(saveFile.apply(image3));

            newsService.saveNews(news);

            redirectAttributes.addFlashAttribute("successMessage", "News uploaded successfully and is pending approval.");
            return "redirect:/reporter/dashboard";

        } catch (RuntimeException e) {
            System.err.println("Error during news upload: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to submit news: " + e.getMessage());
            return "redirect:/reporter/uploadNews";
        }
    }
}
