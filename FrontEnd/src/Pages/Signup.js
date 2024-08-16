import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/client/Signup.css"; // Import the CSS file for styling

export default function Signup() {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmpass, setConfirmpass] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const handleForm = async (e) => {
    e.preventDefault();

    // Check if passwords match
    if (password !== confirmpass) {
      setErrorMessage("Passwords do not match");
      return;
    }

    const userdata = {
      userName: userName,
      email: email,
      password: password,
      mobileNumber: mobileNumber,
    };

    console.log("User data being sent:", userdata);

    try {
      // Adjust the URL based on your backend's URL if necessary
      const response = await axios.post(
        "http://localhost:8080/users",
        userdata
      );
      alert("use registered successfully")
      console.log("User registered successfully:", response.data);
      navigate("/login"); // Navigate to the login page on success
    } catch (err) {
      console.error("Error registering user:", err);
      if (err.response) {
        const errorMessage = err.response.data.message || "An error occurred.";
        setErrorMessage(errorMessage);
      } else {
        setErrorMessage("Registration failed. Please try again.");
      }
    }
  };

  return (
    <div className="signup">
      <div className="signup-container">
        <h1>Sign Up</h1>
        <form onSubmit={handleForm}>
          <label>Username:</label>
          <input
            type="text"
            name="username"
            onChange={(e) => {
              setUserName(e.target.value);
            }}
          />
          <label>Email:</label>
          <input
            type="email"
            name="email"
            onChange={(e) => {
              setEmail(e.target.value);
            }}
          />
          <label>Password:</label>
          <input
            type="password"
            name="password"
            onChange={(e) => {
              setPassword(e.target.value);
            }}
          />
          <label>Confirm Password:</label>
          <input
            type="password"
            name="confirmpassword"
            onChange={(e) => {
              setConfirmpass(e.target.value);
            }}
          />
          <label>Mobile Number:</label>
          <input
            type="text"
            name="mobileNumber"
            onChange={(e) => {
              setMobileNumber(e.target.value);
            }}
          />
          <button type="submit">Register</button>
        </form>
        {errorMessage && (
          <p className="error-message">{errorMessage}</p>
        )}{" "}
        {/* Display error message if any */}
      </div>
    </div>
  );
}