import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../styles/freelancer/RegisterFreelancer.css";

export default function RegisterFreelancer() {
  const [formData, setFormData] = useState({
    freelancerName: "",
    email: "",
    password: "",
    skills: "",
    mobileNumber: "",
    profileDescription: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate mobile number
    if (
      formData.mobileNumber.length !== 10 ||
      !/^\d+$/.test(formData.mobileNumber)
    ) {
      alert("Mobile number must be exactly 10 digits.");
      return;
    }
    const registrationData = {
      freelancerName: formData.freelancerName,
      email: formData.email,
      password: formData.password,
      skills: formData.skills,
      mobileNumber: formData.mobileNumber,
      profileDescription: formData.profileDescription,
    };
    // Submit form data to the server
    try {
      const response = await axios.post(
        "http://localhost:8080/freelancers",
        registrationData
      );
      console.log(response.data);
      alert("Freelancer Registered Successfully!!!");
      navigate("/login");
    } catch (error) {
      if (error.response) {
        const errorMessage = error.response.data.message || "An error occurred.";
        if (errorMessage.includes("Email Already Exists!")) {
          alert("The email is already registered. Please use a different email.");
        } else if (errorMessage.includes("Query did not return a unique result:")) {
          alert("The mobile number is already registered. Please use a different mobile number.");
        } else {
          alert(errorMessage);
        }
      } else {
        console.error(error);
        alert("An unexpected error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="register">
      <div className="register-container">
        <h1>Register as a Freelancer</h1>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="freelancerName">Username:</label>
            <input
              type="text"
              id="username"
              name="freelancerName"
              value={formData.freelancerName}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label htmlFor="skills">Skills:</label>
            <textarea
              id="skills"
              name="skills"
              value={formData.skills}
              onChange={handleChange}
              rows="4"
            />
          </div>
          <div>
            <label htmlFor="mobileNumber">Mobile Number:</label>
            <input
              id="mobileNo"
              name="mobileNumber"
              value={formData.mobileNumber}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label htmlFor="profileDescription">Profile Description:</label>
            <textarea
              id="profileDescription"
              name="profileDescription"
              value={formData.profileDescription}
              onChange={handleChange}
              rows="4"
            />
          </div>
          <button type="submit">Register</button>
        </form>
      </div>
    </div>
  );
}