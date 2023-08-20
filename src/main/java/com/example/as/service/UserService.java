package com.example.as.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.as.model.ApplicationUser;
import com.example.as.model.Role;
import com.example.as.repository.RoleRepository;
import com.example.as.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(username).orElseThrow();
	}
	
	public ApplicationUser registerUser(String userName,String emailId, String password) {
		
		Role userRole = roleRepository.findByAuthority("USER").get();
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(userRole);
		
		return userRepository.save(new ApplicationUser(userName,emailId,encoder.encode(password),roles));
	}

}