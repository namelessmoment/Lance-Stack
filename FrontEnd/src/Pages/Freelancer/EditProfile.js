import React, { useEffect ,useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/freelancer/EditProfile.css';
import axios from 'axios';


// const registrationData = JSON.parse(sessionStorage.getItem("registrationData"))
const freelancerData = JSON.parse(sessionStorage.getItem("freelancerData"))
console.log(freelancerData)
// const updateProfile = {
//   name = freelancerData.name,
//   email = freelancerData.email
// }
// axios.get/
const EditProfile = () => {
  const [profilePicture, setProfilePicture] = useState(null); // No default image, use CSS for default logo
  const [name, setName] = useState(freelancerData.freelancerName);
  const [email, setEmail] = useState(freelancerData.email);
  const [contactNo, setContactNo] = useState(freelancerData.contactNo);
  const [profileDescription, setProfileDescription] = useState(freelancerData.profileDescription);
  const [skills, setSkills] = useState(freelancerData.skills);
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');
  const [dataFetched, setDataFetched] = useState(false);
  const [freelancer ,setFreelancer] = useState(null);

  const navigate = useNavigate(); 

  useEffect(() => {
    if (!dataFetched) {
      fetchMobileAndPassword((freelancerData.id));
      setDataFetched(true);
    }
  }, [dataFetched]);

  const fetchMobileAndPassword = async (freelancerId) => {
    try {
      const response = await axios.post(`http://localhost:8080/freelancers/freelancerMobilePassword/${freelancerId}`);
      const { password, mobileNumber } = response.data;
      setOldPassword(password);
      setContactNo(mobileNumber);
    } catch (error) {
      console.error("Error fetching mobile and password", error);
      alert('Error fetching mobile number and password. Please try again later.');
    }
  };


  const handleProfilePictureChange = (e) => {
    // Simulate changing profile picture
    setProfilePicture(URL.createObjectURL(e.target.files[0]));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (contactNo.length !== 10) {
      alert("Mobile number must be 10 digits");
      return;
    }

    if (newPassword !== confirmNewPassword) {
      alert('New passwords do not match!');
      return;
    }

    const updatedProfile = {
      freelancerName: name,
      // freelancerData.freelancerName = name;
      email: email,
      mobileNumber: contactNo,
      profileDescription: profileDescription,
      skills: skills,
      oldPassword: oldPassword,
      newPassword: newPassword
    };
    console.log('Updated Profile:', updatedProfile);

    // Update the freelancerData object
freelancerData.freelancerName = updatedProfile.freelancerName;
freelancerData.email = updatedProfile.email;
freelancerData.mobileNumber = updatedProfile.mobileNumber;
freelancerData.profileDescription = updatedProfile.profileDescription;
freelancerData.skills = updatedProfile.skills;

// Update the session storage
sessionStorage.setItem("freelancerData", JSON.stringify(freelancerData));
    try {
      // console.log("Hello")
      // Make the PUT request to update the freelancer profile
      await axios.put(`http://localhost:8080/freelancers/updateProfile/${freelancerData.id}`, updatedProfile);
      // Show success message
      alert('Profile updated successfully!');
      navigate('/freelancer');
    } catch (error) {
      console.error("Error updating profile", error);
      alert('Error updating profile. Please try again later.');
    }
  };

  return (
    <div className="edit-profile">
      <h1>Edit Profile</h1>
      <div className="profile-picture">
        <img
          src={profilePicture || '/path/to/default-logo.png'} // Replace with your logo path
          alt="Profile"
          className="profile-img"
        />
        <input
          type="file"
          accept="image/*"
          id="profilePictureInput"
          onChange={handleProfilePictureChange}
        />
        <label htmlFor="profilePictureInput" className="update-button">
          Change Profile Picture
        </label>
      </div>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Change Name</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Change Email Address</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
        <label htmlFor="contactNo">Change Contact Number</label>
        <input type="tel" id="contactNo" value={contactNo} onChange={(e) => setContactNo(e.target.value)} required />
        {contactNo && (contactNo.length < 10 || contactNo.length > 10) && <div style={{ color: 'red' }}>Mobile number must be 10 digits</div>}
      </div>
        <div className="form-group">
            <label htmlFor="profileDescription">Profile Description:</label><br />
            <textarea
              id="profileDescription"
              name="profileDescription"
              value={profileDescription}
              onChange={(e) => setProfileDescription(e.target.value)}
              rows="4"
              cols="50"
          />
          </div>
        <div className="form-group">
          <label htmlFor="skills">Change Skills</label>
          <input
            type="text"
            id="skills"
            value={skills}
            onChange={(e) => setSkills(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="oldPassword">Enter Old Password</label>
          <input
            type="password"
            id="oldPassword"
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="newPassword">Enter New Password</label>
          <input
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="confirmNewPassword">Confirm New Password</label>
          <input
            type="password"
            id="confirmNewPassword"
            value={confirmNewPassword}
            onChange={(e) => setConfirmNewPassword(e.target.value)}
          />
        </div>
        <button type="submit" className="submit-button">  
          Save Changes
        </button>
      </form>
    </div>
  );
};

export default EditProfile;
