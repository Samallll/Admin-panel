package com.example.as.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.as.model.LoginDTO;
import com.example.as.model.RegistrationDTO;
import com.example.as.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
//	For Login
	@GetMapping({"/", "/login"})
	public String loginPage(Model model) {
//		To hold the data
		LoginDTO userLoginAccount = new LoginDTO();
		model.addAttribute("userLoginAccount", userLoginAccount);
		return "login.html";
	}
	
//	For Registration
	@GetMapping("/register")
	public String registerPage(Model model) {

//		To hold the data
		RegistrationDTO newUser = new RegistrationDTO();
		model.addAttribute("newUser", newUser);
		return "register.html";
	}
	
	@PostMapping("/register/new")
	public String saveRegister(@ModelAttribute("newUser") RegistrationDTO r) {
		
		userService.registerUser(r.getUserName(),r.getEmailId(), r.getPassword());
		System.out.println("Registration Completed");
		return "redirect:/login";
	}
}
