package com.example.as.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.as.model.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser,Integer>{
	
	ApplicationUser findByUserName(String username);

}
