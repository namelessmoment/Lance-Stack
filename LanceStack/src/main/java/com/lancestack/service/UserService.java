package com.lancestack.service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.User;

public interface UserService {
	ApiResponse createUser(User user);
}
