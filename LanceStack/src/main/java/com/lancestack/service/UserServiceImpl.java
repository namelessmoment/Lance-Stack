package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.User.UserDTO;
import com.lancestack.dto.User.UserForgetPassword;
import com.lancestack.dto.User.UserLoginDTO;
import com.lancestack.dto.User.UserRegistrationDTO;
import com.lancestack.entities.User;
import com.lancestack.repository.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ApiResponse registerUser(UserRegistrationDTO user) {
		String msg = "User not valid!";
	    if(user == null) {
	        msg = "User contains Null!";
	        throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "User is Null");
	    }
	    User existingUser = userRepo.findByEmail(user.getEmail());
	    if(existingUser != null) {
	    	msg = "User Already Exists!";
	        throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "User Already Exists!");
	    } else {
	        User user1 = modelMapper.map(user, User.class);
	        userRepo.save(user1);
	        msg = "User Registered Successfully.";
	    }
	    return new ApiResponse(msg);

	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepo.findAllUsersNative();
		return users.stream()
				.map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO getUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound(HttpStatus.BAD_REQUEST,"Invalid Id"));
			
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public ApiResponse updateUser(Long id, UserRegistrationDTO user) { //used userRegistrationDTO as it has all fields required for updation
		String msg = "Updation Failed";
			User existingUser = userRepo.findById(id).orElse(null);
			if(existingUser==null) {
				throw new ResourceNotFound(HttpStatus.BAD_REQUEST,"Invalid User ID!!!");
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

	@Override
	public ApiResponse deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFound(HttpStatus.BAD_REQUEST,"Invalid Id"));
		userRepo.delete(user);
		return new ApiResponse("User Deleted Successfully");
	}

	@Override
	public UserDTO getUserByEmailAndPassword(UserLoginDTO userLoginDTO) {
		User user = userRepo.findByEmail(userLoginDTO.getEmail());
	    if(user == null) {
	        throw new ResourceNotFound(HttpStatus.NOT_FOUND, "User not found with email: " + userLoginDTO.getEmail());
	    }
	    if(!user.getPassword().equals(userLoginDTO.getPassword())) {
	        throw new ResourceNotFound(HttpStatus.NOT_FOUND, "Incorrect User Password!");
	    }
	    UserDTO userDto = modelMapper.map(user, UserDTO.class);
	    return userDto;

	}

	@Override
	public ApiResponse forgetPassword(UserForgetPassword user) {
		String msg = "Password Not Changed!";
	    User existingUser = userRepo.findByEmail(user.getEmail());
	    if (existingUser == null) {
	        throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "User Not Exists!");
	    }
	    existingUser.setPassword(user.getPassword());
	    userRepo.save(existingUser);
	    msg = "Password Changed Successfully!";
	    return new ApiResponse(msg);
	}

	
//	private String hashPassword(String password) {
//	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//	    return bCryptPasswordEncoder.encode(password);
//	}

	
//	@Override
//	public UserDTO userLogin(UserLoginDTO userLoginDTO) {
//		User user = userRepo.findByEmail(userLoginDTO.getEmail());
//		if(!user.getPassword().equals(userLoginDTO.getPassword())) {
////			msg = "Incorrect Password!";
//			throw new ResourceNotFound("Incorrect Password!");
//		}
//		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//		return userDTO;
//	}
	
}
