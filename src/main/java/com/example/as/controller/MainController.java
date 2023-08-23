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

import jakarta.servlet.http.HttpSession;

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
	public String registerPage(Model model,HttpSession session) {

//		To hold the data
		RegistrationDTO newUser = new RegistrationDTO();
		model.addAttribute("newUser", newUser);

		// Check if there is a failure message in the session.
		String failMessage = (String) session.getAttribute("fail");
		if (failMessage != null) {
			model.addAttribute("fail", failMessage);
		}

		// Check if there is a success message in the session.
		String successMessage = (String) session.getAttribute("msg");
		if (successMessage != null) {
			model.addAttribute("msg", successMessage);
		}

		return "register.html";
	}
	
	@PostMapping("/register/new")
	public String saveRegister(@ModelAttribute("newUser") RegistrationDTO r, HttpSession session) {

	    // Check if the username already exists.
	    boolean validUserName = userService.isValidUserName(r.getUserName());
	    if (!validUserName) {
	        session.setAttribute("fail", "User Name alreday exists");
	        return "redirect:/register";
	    }

	    // Register the user.
	    userService.registerUser(r.getUserName(), r.getEmailId(), r.getPassword());

	    // Set the success message in the session.
	    session.setAttribute("msg", "Register Sucessfully");

	    // Redirect to the register page.
	    return "redirect:/register";
	}
	
//	For handling access denied exception
	@GetMapping("/access-denied")
	public String accessDenied() {

		return "access-denied.html";
	}
}
