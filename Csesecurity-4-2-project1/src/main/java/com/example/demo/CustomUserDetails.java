package com.example.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String authorityRole; // Stored role with ROLE_ prefix

    public CustomUserDetails(String username, String password, String role) {
        this.username = username;
        this.password = password;
        // FINAL FIX: Ensure the role string always has the "ROLE_" prefix 
        // to satisfy Spring Security's authorization requirements.
        this.authorityRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authorityRole));
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }

    // Standard non-expired/non-locked checks are true for simplicity
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
