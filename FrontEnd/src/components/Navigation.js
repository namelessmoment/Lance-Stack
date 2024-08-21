import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom'; // Import useNavigate and Link for navigation

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

    // Handle logout event in other tabs
    const handleStorageChange = (event) => {
      if (event.key === 'logout') {
        sessionStorage.clear();
        navigate('/login');
      }
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, [navigate]);

  const handleSignOut = () => {
    // Clear all session storage
    sessionStorage.clear();
    console.log(sessionStorage);

    // Inform other tabs about the logout
    localStorage.setItem('logout', Date.now());

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
                  <Link to="/clientpage">Home</Link> {/* Ensure proper routes */}
                </li>
                <li>
                  <Link to="/projects">Projects</Link>
                </li>
                <li>
                  <Link to="/FreelancerProfile">Freelancers</Link>
                </li>
                <li>
                  <Link to="/ClientProfile">Profile</Link>
                </li>
                <li>
                  <Link to="/user-contracts">Contracts</Link>
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
