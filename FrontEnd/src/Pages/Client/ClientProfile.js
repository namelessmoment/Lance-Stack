import React, { useState, useEffect } from "react";
import "../../styles/client/clientpage.css";
import Navigation from "../../components/Navigation";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../components/useAuth";

export default function ClientProfile() {
  useAuth();
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    oldPassword: '', // Field for old password
    newPassword: '', // Field for new password
    confirmPassword: '', // Field for confirm password
  });
  
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Fetch user data from session storage
  const user = JSON.parse(sessionStorage.getItem('clientData')) || JSON.parse(sessionStorage.getItem('freelancerData'));
  const userId = user ? user.id : null;
  console.log(user);

  const navigate = useNavigate(); 
  // const user = JSON.parse(sessionStorage.getItem('clientData'))
  // Prefill the form fields with session data on component mount
  useEffect(() => {
    if (userId) {
      axios.post(`http://localhost:8080/users/userMobilePassword/${userId}`)
        .then(response => {
          const { mobileNumber, password } = response.data;
          setFormData({
            name: user.userName, // Adjust the key if necessary
            email: user.email,
            phone: user.mobileNumber, // Adjust the key if necessary
            oldPassword: password,  // Populate with fetched password
            newPassword: '',  // Initialize with an empty string
            confirmPassword: '',  // Initialize with an empty string
          });
        })
        .catch(error => {
          console.error('Error fetching user password:', error);
          setErrorMessage('Failed to load user data.');
        });
        // console.log("test") 
    }
  }, [userId]);

  // Handle input change
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Basic validation
  const validate = () => {
    let tempErrors = {};
    let isValid = true;

    if (!formData.name) {
      tempErrors.name = 'Name is required';
      isValid = false;
    }

    if (!formData.email) {
      tempErrors.email = 'Email is required';
      isValid = false;
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      tempErrors.email = 'Email is invalid';
      isValid = false;
    }

    if (!formData.phone) {
      tempErrors.phone = 'Phone number is required';
      isValid = false;
    } else if (!/^\d{10}$/.test(formData.phone)) {
      tempErrors.phone = 'Phone number is invalid';
      isValid = false;
    }

    if (!formData.oldPassword) {
      tempErrors.oldPassword = 'Old password is required';
      isValid = false;
    }

    // if (!formData.newPassword) {
    //   tempErrors.newPassword = 'New password is required';
    //   isValid = false;
    // } else if (formData.newPassword === formData.oldPassword) {
    //   tempErrors.newPassword = 'New password cannot be the same as the old password';
    //   isValid = false;
    // }

    // if (!formData.confirmPassword) {
    //   tempErrors.confirmPassword = 'Confirm password is required';
    //   isValid = false;
    // } else if (formData.newPassword !== formData.confirmPassword) {
    //   tempErrors.confirmPassword = 'Passwords do not match';
    //   isValid = false;
    // }

    setErrors(tempErrors);
    return isValid;
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      // Prepare the data to be sent to the backend server
      const updateData = {
        userName: formData.name,
        email: formData.email,
        mobileNumber: formData.phone,
        oldPassword: formData.oldPassword,
        newPassword: formData.newPassword,
    };
    console.log('Updated Profile:', updateData);

    //Update the user 
    user.userName = updateData.userName;
    user.email = updateData.email;
    user.mobileNumber = updateData.mobileNumber;

    // Update the session storage
    sessionStorage.setItem("clientData", JSON.stringify(user));

    axios.put(`http://localhost:8080/users/updateProfile/${userId}`, updateData)
        .then(response => {
            if (response.status === 200) { // Check if the response status is OK
                console.log('Profile updated successfully:', response.data);
                setSuccessMessage('Profile updated successfully!');
                setErrorMessage('');

                window.alert('Profile updated successfully!');
                // Optionally, clear the success message after some time
                setTimeout(() => setSuccessMessage(''), 3000);
                navigate('/clientpage')

            } else {
                console.error('Unexpected response status:', response.status);
                setErrorMessage('Failed to update profile.');
            }
        })
        .catch(error => {
            console.error('Error updating profile:', error);
            setErrorMessage('Failed to update profile.');
        });
}
};

  return (
    <div>
      <Navigation />
      <div className="clientprofile">
        <h2>Update Profile</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              className="form-control"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
            />
            {errors.name && <p className="text-danger">{errors.name}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
            {errors.email && <p className="text-danger">{errors.email}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="phone">Phone Number:</label>
            <input
              type="tel"
              className="form-control"
              id="phone"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
            />
            {errors.phone && <p className="text-danger">{errors.phone}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="oldPassword">Old Password:</label>
            <input
              type="password"
              className="form-control"
              id="oldPassword"
              name="oldPassword"
              value={formData.oldPassword}
              onChange={handleChange}
            />
            {errors.oldPassword && <p className="text-danger">{errors.oldPassword}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="newPassword">New Password:</label>
            <input
              type="password"
              className="form-control"
              id="newPassword"
              name="newPassword"
              value={formData.newPassword}
              onChange={handleChange}
            />
            {errors.newPassword && <p className="text-danger">{errors.newPassword}</p>}
          </div>

          <div className="form-group">
            <label htmlFor="confirmPassword">Confirm Password:</label>
            <input
              type="password"
              className="form-control"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
            {errors.confirmPassword && <p className="text-danger">{errors.confirmPassword}</p>}
          </div>

          <button type="submit" className="btn btn-primary">
            Update
          </button>
        </form>
        {successMessage && <p className="text-success">{successMessage}</p>}
        {errorMessage && <p className="text-danger">{errorMessage}</p>}
      </div>
    </div>
  );
}
