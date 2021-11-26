package com.example.demo;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@SpringBootApplication
public class SecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		
		return ArgsAnnotationPointcut -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			
			userService.saveUser(new User(null, "Akshay", "akshay", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Arun", "arun", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Shilpa", "shilpa", "1234", new ArrayList<>()));
			
			userService.addRoleToUser("akshay", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arun", "ROLE_ADMIN");
			userService.addRoleToUser("shilpa", "ROLE_USER");
			
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
	
	

}
