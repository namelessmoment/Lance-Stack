import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation

export default function Navigation() {
  const [userName, setUserName] = useState(''); // State to store the username
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    // Retrieve user data from session storage
    const userData = sessionStorage.getItem('clientData') || sessionStorage.getItem('freelancerData');
    if (userData) {
      const parsedData = JSON.parse(userData);
      setUserName(parsedData.userName || ''); // Update state with the username
    }
  }, []);

  const handleSignOut = () => {
    // Clear all session storage
    sessionStorage.clear();
    console.log(sessionStorage);

    // Redirect to login page
    navigate('/login');
  };

  return (
    <div className="body">
      <header>
        <div className="container1">
          <nav>
            <ul>
              <div className="leftel">
                <h4>Hello {userName || 'User'}</h4> {/* Display the username */}
              </div>
              <div className="container2">
                <li>
                  <a href="/clientpage">Home</a> {/* Ensure proper routes */}
                </li>
                <li>
                  <a href="/projects">Projects</a>
                </li>
                <li>
                  <a href="/FreelancerProfile">Freelancers</a>
                </li>
                <li>
                  <a href="/ClientProfile">Profile</a>
                </li>
                <li>
                  <a href="/user-contracts">Contracts</a>
                </li>
              </div>
              <div className="rightel">
                <button onClick={handleSignOut} className="btn btn-danger">
                  Sign Out
                </button>
              </div>
            </ul>
          </nav>
        </div>
      </header>
    </div>
  );
}