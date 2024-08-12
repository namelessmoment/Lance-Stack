package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.VerifyOTPDTO;
import com.lancestack.service.OTPEntityService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/forget-pass")
public class OTPController {
	
	@Autowired
	OTPEntityService otpService;
	
	@Operation(description = "Send OTP for Password Reset (For User)")
    @PostMapping("/forgotpassword/user")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(otpService.sendOtpForPasswordReset(email));
		}
		catch (RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage()));
		}
    }
	
	@Operation(description = "Verify OTP and Reset Password (For User)")
    @PostMapping("/verify-otp/user")
    public ResponseEntity<?> verifyOtpAndResetPassword(@Validated @RequestBody VerifyOTPDTO verifyOtpDTO) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(otpService.verifyOtpAndResetPassword(verifyOtpDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage()));
		}
    }
	
	
	@Operation(description = "Send OTP for Password Reset (For Freelancer)")
    @PostMapping("/forgotpassword/freelancer")
    public ResponseEntity<?> forgotPasswordFreelancer(@RequestParam String email) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(otpService.sendOtpForPasswordResetFreelancer(email));
		}
		catch (RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage()));
		}
    }
	
	@Operation(description = "Verify OTP and Reset Password (For Freelancer)")
    @PostMapping("/verify-otp/freelancer")
    public ResponseEntity<?> verifyOtpAndResetPasswordFreelancer(@Validated @RequestBody VerifyOTPDTO verifyOtpDTO) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(otpService.verifyOtpAndResetPasswordFreelancer(verifyOtpDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage()));
		}
    }
}
