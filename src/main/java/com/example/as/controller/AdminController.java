package com.example.as.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.as.model.ApplicationUser;
import com.example.as.model.RegistrationDTO;
import com.example.as.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String adminHome(Model model) {

		model.addAttribute("userList",userService.getAllUsers());
		return "adminHome.html";
	}
	
//	For Registration
	@GetMapping("/register/")
	public String registerUser(Model model) {

//		To hold the data
		RegistrationDTO newUser = new RegistrationDTO();
		model.addAttribute("newUser", newUser);
		return "register.html";
	}
	
	@PostMapping("/register/new/")
	public String saveRegister(@ModelAttribute("newUser") RegistrationDTO r) {
		
		userService.registerUser(r.getUserName(),r.getEmailId(), r.getPassword());
		System.out.println("Registration Completed");
		return "adminHome.html";
	}
}

