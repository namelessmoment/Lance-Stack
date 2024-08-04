package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancestack.dto.ApiResponse;
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
	
	@GetMapping()
	public ResponseEntity<?> getAllUsers(){
		return ResponseEntity
				.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Long userId , @RequestBody User user){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId,user));
	}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
}
}
