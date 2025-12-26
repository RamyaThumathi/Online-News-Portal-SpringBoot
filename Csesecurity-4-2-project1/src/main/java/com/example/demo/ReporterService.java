package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ReporterService {

    @Autowired
    private ReporterRepository1 reporterRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Reporter registerReporter(Reporter reporter) {
        reporter.setPassword(passwordEncoder.encode(reporter.getPassword()));
        reporter.setRole("REPORTER");
        return reporterRepo.save(reporter);
    }

    public Reporter getByUsername(String username) {
        return reporterRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
    }
}
