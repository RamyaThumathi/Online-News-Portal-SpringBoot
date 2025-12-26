package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private AdminRepository adminRepo;
    @Autowired private ReporterRepository1 reporterRepo;
    @Autowired private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepo.findByUsername(username)
                .map(a -> new CustomUserDetails(a.getUsername(), a.getPassword(), a.getRole()))
                .or(() -> reporterRepo.findByUsername(username)
                        .map(r -> new CustomUserDetails(r.getUsername(), r.getPassword(), r.getRole())))
                .or(() -> userRepo.findByUsername(username)
                        .map(u -> new CustomUserDetails(u.getUsername(), u.getPassword(), u.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
