package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAdmin(Admin admin) {
        // Check if username already exists to prevent database errors (though uniqueness is also enforced in the entity)
        if (adminRepo.findByUsername(admin.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        
        // Encode password before saving to DB
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        
        // Ensure the role is set to the clean value "ADMIN" for consistency. 
        // CustomUserDetails will add the "ROLE_" prefix later.
        admin.setRole("ADMIN"); 
        
        adminRepo.save(admin);
    }
}
