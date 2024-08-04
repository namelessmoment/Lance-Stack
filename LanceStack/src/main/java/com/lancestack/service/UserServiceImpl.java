package com.lancestack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
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

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Invalid Id"));
			
		return user;
	}

	@Override
	public ApiResponse updateUser(Long id, User user) {
		String msg = "Updation Failed";
			User existingUser = userRepo.findById(id).orElse(null);
			if(existingUser==null) {
				throw new ResourceNotFound("Invalid User ID!!!");
			}
			else {
			existingUser.setEmail(user.getEmail());
			existingUser.setMobileNumber(user.getMobileNumber());
			existingUser.setPassword(user.getPassword());
			existingUser.setUserName(user.getUserName());
			msg = "Updated Successfully";
		}
		return new ApiResponse(msg);
	}
	
}
