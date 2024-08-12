package com.lancestack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.OTPEntity;

@Repository
public interface OTPEntityRepository extends JpaRepository<OTPEntity, Long> {

	Optional<OTPEntity> findByEmailAndOtp(String email, String otp);

	OTPEntity findByOtp(String otp);

}
