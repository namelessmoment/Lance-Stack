package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.User;

public interface UserService {
	ApiResponse createUser(User user);
	
	List<User> getAllUsers();
	
	User getUser(Long id);
	
	ApiResponse updateUser(Long id, User user);
}
