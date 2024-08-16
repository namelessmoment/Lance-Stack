import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/VerifyOtp.css";

const VerifyOtp = () => {
  const [otp, setOtp] = useState({ otp1: "", otp2: "", otp3: "", otp4: "" });
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isOtpValid, setIsOtpValid] = useState(null); // Track OTP validation state
  const navigate = useNavigate();

  // Retrieve email and role from sessionStorage when the component mounts
  const email = sessionStorage.getItem("email");
  const role = sessionStorage.getItem("role");

  const handleOtpChange = (e) => {
    const { name, value } = e.target;
    setOtp({ ...otp, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const fullOtp = `${otp.otp1}${otp.otp2}${otp.otp3}${otp.otp4}`;
    try {
      const apiUrl =
        role === "client"
          ? "http://localhost:8080/api/forget-pass/verify-otp/user"
          : "http://localhost:8080/api/forget-pass/verify-otp/freelancer";

      const payload = {
        email,
        otp: fullOtp,
        newPassword,
        confirmPassword,
      };

      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload), // Send OTP and new password
      });

      if (response.ok) {
        console.log("Password reset successfully");
        alert("Password reset successfully");

        navigate("/login");
      } else {
        setIsOtpValid(false);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="verify-otp">
      <div className="verify-otp-container">
        <h2>Verify OTP</h2>
        <form onSubmit={handleSubmit}>
          <label htmlFor="otp">Enter OTP</label>
          <div className="otp-inputs">
            <input
              type="text"
              name="otp1"
              maxLength="1"
              value={otp.otp1}
              onChange={handleOtpChange}
              required
            />
            <input
              type="text"
              name="otp2"
              maxLength="1"
              value={otp.otp2}
              onChange={handleOtpChange}
              required
            />
            <input
              type="text"
              name="otp3"
              maxLength="1"
              value={otp.otp3}
              onChange={handleOtpChange}
              required
            />
            <input
              type="text"
              name="otp4"
              maxLength="1"
              value={otp.otp4}
              onChange={handleOtpChange}
              required
            />
          </div>
          <div className="password-container">
            <label htmlFor="newPassword">New Password</label>
            <input
              type="password"
              id="newPassword"
              name="newPassword"
              required
              onChange={(e) => setNewPassword(e.target.value)}
            />
          </div>
          <div className="password-container">
            <label htmlFor="confirmPassword">Confirm New Password</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              required
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </div>
          <button type="submit">Submit</button>
          {isOtpValid === false && (
            <p className="error">Invalid OTP or Passwords do not match</p>
          )}
        </form>
      </div>
    </div>
  );
};

export default VerifyOtp;
