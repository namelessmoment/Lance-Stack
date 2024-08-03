package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancestack.entities.User;
import com.lancestack.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userService.createUser(user));
	}
	
//	@GetMapping()
//	public ResponseEntity<User> getAllUsers(Long id){
//		return ResponseEntity
//				.status(HttpStatus.CREATED)
//				.body(userService.createUser(user));
//	}
}
