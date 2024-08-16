import React, { useState } from "react";
import "../styles/Login.css";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("client"); // Default to client
  const [errorMessage, setErrorMessage] = useState(""); // State to track error messages
  const navigate = useNavigate();

  async function loginFunc(e) {
    e.preventDefault(); // Prevent form from submitting the default way
    console.log("Login button clicked");

    const url = role === "freelancer" 
      ? "http://localhost:8080/freelancers/freelancerLogin" 
      : "http://localhost:8080/users/userLogin";

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email: username, password }), // Use 'email' instead of 'username' to match API
      });

      if (response.ok) {
        const data = await response.json();
        if (data) {
          console.log("Login successful:", data);
          // Save the response in session storage
          const storageKey = role === "freelancer" ? "freelancerData" : "clientData";
          sessionStorage.setItem(storageKey, JSON.stringify(data));
          const clientData = JSON.parse(sessionStorage.getItem("data"))
          console.log(clientData);
          navigate(role === "freelancer" ? "/freelancer" : "/clientpage");
        } else {
          setErrorMessage("Invalid user or password"); // Set error message for null data
        }
      } else {
        setErrorMessage("Invalid user or password"); // Set error message for non-OK response
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage("An error occurred during login. Please try again."); // Set error message for catch block
    }
  }

  return (
    <div>
      <div className="login">
        <div className="login-container">
          <h2>LanceStack</h2>
          <form onSubmit={loginFunc}>
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              required
              onChange={(e) => setUsername(e.target.value)}
            />

            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              required
              onChange={(e) => setPassword(e.target.value)}
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

            <button type="submit">Login</button>
          </form>
          {errorMessage && <div className="error-message">{errorMessage}</div>} {/* Display error message */}
          <div className="footer">
            <p>
              Don't have an account?{" "}
              <a href={role === "freelancer" ? "/registerfreelancer" : "/signup"}>
                Sign up
              </a>

            </p>
            <p>
              <a href="/forget-password">Forget Password?</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
