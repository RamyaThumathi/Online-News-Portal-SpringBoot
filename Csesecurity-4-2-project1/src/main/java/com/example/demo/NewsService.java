package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepo;

    @Autowired
    private ReporterRepository1 reporterRepo;  // renamed from ReporterRepository1

    public News saveNews(News news) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Reporter> reporterOpt = reporterRepo.findByUsername(username);

        if (reporterOpt.isEmpty()) {
            throw new RuntimeException("Error: Could not find Reporter associated with username: " + username);
        }
        news.setReporter(reporterOpt.get());
        return newsRepo.save(news);
    }

    public List<News> findAllNews() {
        return newsRepo.findAll();
    }
    
    public List<News> findNewsByStatus(String status) {
        return newsRepo.findByStatusIgnoreCase(status);
    }
}
