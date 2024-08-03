package com.lancestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.User;
import com.lancestack.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public ApiResponse createUser(User user) {
		userRepo.save(user);
		return new ApiResponse("User Added.");
	}
	
	
}
