package com.lancestack.service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.VerifyOTPDTO;

public interface OTPEntityService {

	ApiResponse sendOtpForPasswordReset(String email);

	ApiResponse verifyOtpAndResetPassword(VerifyOTPDTO verifyOtpDTO);

	ApiResponse sendOtpForPasswordResetFreelancer(String email);

	ApiResponse verifyOtpAndResetPasswordFreelancer(VerifyOTPDTO verifyOtpDTO);

}
