package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.User.UserDTO;
import com.lancestack.dto.User.UserLoginDTO;
import com.lancestack.dto.User.UserRegistrationDTO;
import com.lancestack.entities.User;
import com.lancestack.repository.UserRepository;

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
		User user1 = modelMapper.map(user, User.class);
		userRepo.save(user1);
		return new ApiResponse("User Registered Successfully.");
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
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Invalid Id"));
			
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public ApiResponse updateUser(Long id, UserRegistrationDTO user) { //used userRegistrationDTO as it has all fields required for updation
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

	@Override
	public ApiResponse deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Invalid Id"));
		userRepo.delete(user);
		return new ApiResponse("User Deleted Successfully");
	}

	@Override
	public UserDTO getUserByEmailAndPassword(UserLoginDTO userLoginDTO) {
		User user = userRepo.findByEmail(userLoginDTO.getEmail());
		if(user == null) {
			return null;
		}
		if(!user.getPassword().equals(userLoginDTO.getPassword())) {
			throw new ResourceNotFound("Incorrect User Password!");
		}
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return userDto;
	}

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
