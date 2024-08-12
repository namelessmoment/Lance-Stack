package com.lancestack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOTPDTO {
	@NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "OTP must not be null")
    private String otp; // Use Integer instead of int to allow for null values

    @NotEmpty(message = "New password must not be empty")
    private String newPassword;

    @NotEmpty(message = "Confirm password must not be empty")
    private String confirmPassword;
}
