package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByMobileNumber(String mobileNumber);

	@Query(value = "SELECT * FROM users", nativeQuery = true)
	List<User> findAllUsersNative();
	
	User findByEmail(String email);
	
	boolean existsByEmail(String email);


}
