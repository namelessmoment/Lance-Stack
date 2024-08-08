package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.User.UserDTO;
import com.lancestack.dto.User.UserLoginDTO;
import com.lancestack.dto.User.UserRegistrationDTO;
import com.lancestack.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Operation(description = "Registration of User Endpoint.")
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO user){
		try {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userService.registerUser(user));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Get All users.")
	@GetMapping()
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(userService.getAllUsers());
	}
	
	@Operation(description = "Get user by user ID.")
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Updating the user by using userID and UserResgistrationDTO.")
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Long userId , @RequestBody UserRegistrationDTO user){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId,user));
	}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Delete user by user ID")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable Long userId){
		try {
		return ResponseEntity.ok(userService.deleteUser(userId));
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
//	@PostMapping("/userLogin")
//	public ResponseEntity<UserDTO> userLogin(@RequestBody @Valid UserLoginDTO userLoginDTO){
//	        UserDTO user = userService.userLogin(userLoginDTO);
//	        return ResponseEntity.ok(user);
//	}
	
	@Operation(description = "User Login Endpoint.")
	@PostMapping("/userLogin")
	public ResponseEntity<UserDTO> userLogin(@RequestBody UserLoginDTO userLoginDTO){
		UserDTO userDTO = userService.getUserByEmailAndPassword(userLoginDTO);
		return ResponseEntity.ok(userDTO);
	}

}
