package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService,UserDetailsService {
	
	private final UserRepo userRepo;
	
	private final RoleRepo roleRepo;
	
	private final PasswordEncoder passwordEncoder;
	

	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the databse",user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the databse",role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String usename, String roleName) {
		log.info("Adding role {} to user {} ",roleName,usename);
		User user = userRepo.findByUsername(usename);
		Role role =roleRepo.findByName(roleName);
		user.getRoles().add(role);
		
	}

	@Override
	public User getUser(String userName) {
		
		return userRepo.findByUsername(userName);
	}

	@Override
	public List<User> getUsers() {
		
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUsername(username);
		if(user == null) {
			log.error("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		Collection<SimpleGrantedAuthority> authorities =new  ArrayList<>();
		user.getRoles().forEach(role-> {  authorities.add(new SimpleGrantedAuthority(role.getName())); 
				});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	

}
}
