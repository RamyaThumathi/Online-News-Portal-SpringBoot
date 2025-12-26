package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin6")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    public Long getId() {
		return id;
	}

	public Admin(Long id, String name, String email, String username, String password, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// FINAL FIX: Enforce uniqueness and rely on Lombok for accessors
    // Email and Username must be unique across the system for security
    @Column(unique = true)
    private String email;
    
    @Column(unique = true) 
    private String username;
    
    private String password;
    
    // FINAL FIX: Store the clean role name ("ADMIN"). CustomUserDetails adds "ROLE_" prefix.
    private String role = "ADMIN"; 
    
    // Lombok's @Data handles all getters and setters (getId, getName, getRole, etc.)
}
