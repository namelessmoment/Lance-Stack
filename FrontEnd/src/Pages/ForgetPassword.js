import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ForgetPassword.css"

const ForgetPassword = () => {
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("client"); // Default to client
  const [isEmailValid, setIsEmailValid] = useState(null); // Track email validation state
  const [isLoading, setIsLoading] = useState(false); // Track loading state
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    let apiUrl;

    if (role === "freelancer") {
      apiUrl = `http://localhost:8080/api/forget-pass/forgotpassword/freelancer?email=${encodeURIComponent(email)}`;
    } else if (role === "client") {
      apiUrl = `http://localhost:8080/api/forget-pass/forgotpassword/user?email=${encodeURIComponent(email)}`;
    } else {
      console.log("Invalid role");
      return;
    }

    setIsLoading(true); // Start loading
    setIsEmailValid(null); // Reset email validation state

    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: role === "client" ? JSON.stringify({ email }) : null, // Send email in body only for 'client'
      });

      if (response.ok) {
        sessionStorage.setItem("email", email); // Store email in sessionStorage
        sessionStorage.setItem("role", role); // Store role in sessionStorage
        navigate("/verify-otp"); // Navigate to Verify.js
      } else {
        setIsEmailValid(false);
      }
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setIsLoading(false); // Stop loading
    }
  };

  return (
    <div className="forget-password">
      <div className="forget-password-container">
        <h2>Forget Password</h2>
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            required
            onChange={(e) => setEmail(e.target.value)}
          />
          <div className="role-selection">
            <label>
              <input
                type="radio"
                value="client"
                checked={role === "client"}
                onChange={(e) => setRole(e.target.value)}
              />
              Client
            </label>
            <label>
              <input
                type="radio"
                value="freelancer"
                checked={role === "freelancer"}
                onChange={(e) => setRole(e.target.value)}
              />
              Freelancer
            </label>
          </div>
          <button type="submit" disabled={isLoading}>
            {isLoading ? (
              <span>
                Submit <div className="loading-icon"></div>
              </span>
            ) : (
              "Submit"
            )}
          </button>
          {isEmailValid === false && <p className="error">Email not found</p>}
        </form>
      </div>
    </div>
  );
};

export default ForgetPassword;