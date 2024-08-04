package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByMobileNumber(String mobileNumber);
	User findByEmail(String email);
}
