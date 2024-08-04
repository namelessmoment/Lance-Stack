package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.UserDTO;
import com.lancestack.dto.UserLoginDTO;
import com.lancestack.dto.UserRegistrationDTO;
import com.lancestack.entities.User;
import com.lancestack.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO user){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userService.registerUser(user));
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
	public ResponseEntity<?> updateUser(@PathVariable Long userId , @RequestBody UserRegistrationDTO user){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId,user));
	}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable Long userId){
		try {
		return ResponseEntity.ok(userService.deleteUser(userId));
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	@PostMapping(value ="/userLogin")
	public ResponseEntity<UserDTO> userLogin(@RequestBody UserLoginDTO userLoginDTO){
		UserDTO user = userService.userLogin(userLoginDTO);
		return ResponseEntity.ok(user);
	}
}
