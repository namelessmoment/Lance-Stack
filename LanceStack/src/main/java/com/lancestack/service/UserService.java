package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.User.UserDTO;
import com.lancestack.dto.User.UserLoginDTO;
import com.lancestack.dto.User.UserRegistrationDTO;
import com.lancestack.entities.User;

public interface UserService {
	ApiResponse registerUser(UserRegistrationDTO user);
	
	List<User> getAllUsers();
	
	User getUser(Long id);
	
	ApiResponse updateUser(Long id, UserRegistrationDTO user);
	
	ApiResponse deleteUser(Long id);

	UserDTO getUserByEmailAndPassword(UserLoginDTO userLoginDTO);
}
