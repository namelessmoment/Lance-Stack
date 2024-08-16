import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/freelancer/ProfilePanel.css';

const ProfilePanel = ({ onClose }) => {
  const navigate = useNavigate();
  
  const handleSignOut = () => {
    // Clear all session storage
    sessionStorage.clear();
    console.log(sessionStorage);

    // Redirect to login or home page
    navigate('/login');
  };

  const goToEditProfile = () => {
    navigate('/edit-profile');
  };

  return (
    <div className="profile-panel">
      <button className="close-button" onClick={onClose}>×</button>
      <div className="profile-options">
        <button className="profile-option" onClick={goToEditProfile}>Edit Profile</button>
        <button className="profile-option" onClick={handleSignOut}>Sign Out</button>
      </div>
    </div>
  );
};

export default ProfilePanel;
