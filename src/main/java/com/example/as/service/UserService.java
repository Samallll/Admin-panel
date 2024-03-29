package com.example.as.service;

import java.util.HashSet;
import java.util.List;
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
			ApplicationUser user = userRepository.findByUserName(username);
			
			if (user != null) {
				return user;
			}
	
			throw new UsernameNotFoundException("User not available");
		}
	
	public ApplicationUser registerUser(String userName,String emailId, String password) {
		
		System.out.println("Registering");
//		Made as comments to check the working of registerUser
		Role userRole = roleRepository.findByAuthority("USER").get();
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(userRole);
		
		return userRepository.save(new ApplicationUser(userName,emailId,encoder.encode(password),roles));
	}
	
	public List<ApplicationUser> getAllUsers(){
		
		return userRepository.findAll();
	}

	public ApplicationUser findUserById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}
	
	public ApplicationUser findUserByName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	public void updateUser(Integer id, String userName, String emailId, String password) {
		// TODO Auto-generated method stub
		ApplicationUser editUser = userRepository.findById(id).get();
		editUser.setPassword(encoder.encode(password));
		editUser.setUserName(userName);
		editUser.setEmailId(emailId);
	}
	
	public void changeRole(ApplicationUser editUser) {

		Integer id = editUser.getId();
		userRepository.deleteById(id);
		userRepository.save(editUser);
	}
	
	public boolean checkEmailId(String email) {
//		if(userRepository.findByEmailId(email))
			return false;
	}

	public boolean isValidUserName(String userName) {
		if(userRepository.findByUserName(userName)==null)
			return true;
		else
			return false;
	}

	public boolean isValidEmailId(String emailId) {
		if(userRepository.findByEmailId(emailId)==null)
			return true;
		else
			return false;
	}

}