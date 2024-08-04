package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.UserDTO;
import com.lancestack.dto.UserLoginDTO;
import com.lancestack.dto.UserRegistrationDTO;
import com.lancestack.entities.User;

public interface UserService {
	ApiResponse registerUser(UserRegistrationDTO user);
	
	List<User> getAllUsers();
	
	User getUser(Long id);
	
	ApiResponse updateUser(Long id, UserRegistrationDTO user);
	
	ApiResponse deleteUser(Long id);
	
	UserDTO userLogin(UserLoginDTO userLoginDTO);
}
