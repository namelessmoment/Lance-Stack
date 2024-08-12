package com.lancestack.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFoundException;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.VerifyOTPDTO;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.OTPEntity;
import com.lancestack.entities.User;
import com.lancestack.repository.FreelancerRepository;
import com.lancestack.repository.OTPEntityRepository;
import com.lancestack.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OTPEntityServiceImpl implements OTPEntityService {

	@Autowired
	OTPEntityRepository otpRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	FreelancerRepository freelancerRepo;
	
	@Autowired
    private JavaMailSender mailSender;
	
	private static final SecureRandom random = new SecureRandom();
	
	@Override
	public ApiResponse sendOtpForPasswordReset(String email) {
		
		if(!userRepo.existsByEmail(email)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User Not exists!");
		}
		else {
			// Generate OTP
	        String otp = generateOtp(); // Ensure OtpGenerator is implemented correctly

	        // Save OTP in the database
	        OTPEntity otpEntity = new OTPEntity(email, otp);
	        otpRepo.save(otpEntity);

	        // Send OTP via email
	        sendOtpEmail(email, otp);

	        return new ApiResponse("OTP sent to email successfully");
		}
		
	}
	
	
	@Override
	public ApiResponse verifyOtpAndResetPassword(VerifyOTPDTO verifyOtpDTO) {
		// Check if OTP is null
        if (verifyOtpDTO.getOtp() == null) {
            return new ApiResponse("OTP must not be null");
        }

        // Validate OTP length manually if needed (assuming OTP should be 4 digits)
        String otpString = String.valueOf(verifyOtpDTO.getOtp());
        if (otpString.length() != 4) {
            return new ApiResponse("Invalid OTP length. It should be 4 digits.");
        }

        // Find OTP by email and OTP
        Optional<OTPEntity> otpEntityOptional = otpRepo.findByEmailAndOtp(verifyOtpDTO.getEmail(), verifyOtpDTO.getOtp());

        if (otpEntityOptional.isEmpty()) {
            return new ApiResponse("Invalid OTP");
        }

        // Verify OTP and reset password logic here
        if (!verifyOtpDTO.getNewPassword().equals(verifyOtpDTO.getConfirmPassword())) {
            return new ApiResponse("Passwords do not match");
        }
        OTPEntity entity = otpRepo.findByOtp(verifyOtpDTO.getOtp());
        if(!verifyOtpDTO.getOtp().equals(entity.getOtp())) {
        	return new ApiResponse("OTP Invalid.");
        }
        else {
        	User existingUser = userRepo.findByEmail(verifyOtpDTO.getEmail());
        	if (existingUser == null) {
    	        throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "User Not Exists!");
    	    }
        	existingUser.setPassword(verifyOtpDTO.getNewPassword());
    	    userRepo.save(existingUser);
    	    otpRepo.delete(entity);
    	    return new ApiResponse("Password Changed Successfully!");
        }
	}
	
	private  static String generateOtp() {
        int otp = 1000 + random.nextInt(9000); 
        return String.valueOf(otp);
    }
	
	private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP for Password Reset");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
	}


	@Override
	public ApiResponse sendOtpForPasswordResetFreelancer(String email) {
		if(!freelancerRepo.existsByEmail(email)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Freelancer Not exists!");
		}
		else {
			// Generate OTP
	        String otp = generateOtp(); // Ensure OtpGenerator is implemented correctly

	        // Save OTP in the database
	        OTPEntity otpEntity = new OTPEntity(email, otp);
	        otpRepo.save(otpEntity);

	        // Send OTP via email
	        sendOtpEmail(email, otp);

	        return new ApiResponse("OTP sent to email successfully");
		}
	}


	@Override
	public ApiResponse verifyOtpAndResetPasswordFreelancer(VerifyOTPDTO verifyOtpDTO) {
		// Check if OTP is null
        if (verifyOtpDTO.getOtp() == null) {
            return new ApiResponse("OTP must not be null");
        }

        // Validate OTP length manually if needed (assuming OTP should be 4 digits)
        String otpString = String.valueOf(verifyOtpDTO.getOtp());
        if (otpString.length() != 4) {
            return new ApiResponse("Invalid OTP length. It should be 4 digits.");
        }

        // Find OTP by email and OTP
        Optional<OTPEntity> otpEntityOptional = otpRepo.findByEmailAndOtp(verifyOtpDTO.getEmail(), verifyOtpDTO.getOtp());

        if (otpEntityOptional.isEmpty()) {
            return new ApiResponse("Invalid OTP");
        }

        // Verify OTP and reset password logic here
        if (!verifyOtpDTO.getNewPassword().equals(verifyOtpDTO.getConfirmPassword())) {
            return new ApiResponse("Passwords do not match");
        }
        OTPEntity entity = otpRepo.findByOtp(verifyOtpDTO.getOtp());
        if(!verifyOtpDTO.getOtp().equals(entity.getOtp())) {
        	return new ApiResponse("OTP Invalid.");
        }
        else {
        	Freelancer existingFreelancer = freelancerRepo.findByEmail(verifyOtpDTO.getEmail());
        	if (existingFreelancer == null) {
    	        throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "User Not Exists!");
    	    }
        	existingFreelancer.setPassword(verifyOtpDTO.getNewPassword());
    	    freelancerRepo.save(existingFreelancer);
    	    otpRepo.delete(entity);
    	    return new ApiResponse("Password Changed Successfully!");
        }
	}
}
