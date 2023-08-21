package com.example.as.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		return "create_user.html";
	}
	
	@PostMapping("/register/new/")
	public String saveRegister(@ModelAttribute("newUser") RegistrationDTO r) {
		
		userService.registerUser(r.getUserName(),r.getEmailId(), r.getPassword());
		System.out.println("Registration Completed");
		return "redirect:/admin/";
	}
	
	
//	Editing and deleting an existing data
	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable Integer id, Model model) {
		
//		To hold the date of user data needs to be updated
		ApplicationUser editUser = userService.findStudentById(id);		
		System.out.println(editUser.getUsername());
		System.out.println(editUser.getPassword());
		model.addAttribute("editUser", editUser);
		return "edit_user.html";
	}
	
	@PostMapping("/{id}")
	public String updateUser(@PathVariable Integer id, @ModelAttribute RegistrationDTO r) {
		
		userService.updateUser(id,r.getUserName(),r.getEmailId(),r.getPassword());
		System.out.println("User data updated");
		return "redirect:/admin/";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id, @ModelAttribute RegistrationDTO r) {
		
		userService.deleteUser(id);
		System.out.println("User data deleted");
		return "redirect:/admin/";
	}
}

