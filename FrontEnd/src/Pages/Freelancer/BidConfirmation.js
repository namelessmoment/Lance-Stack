import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../../styles/freelancer/BidCinfirmation.css';
import axios from 'axios';

const BidConfirmation = () => {
  const location = useLocation();
  const [bidAmount, setBidAmount] = useState('');
  const [bidDescription, setBidDescription] = useState('');
  const [daysWillTake, setDaysWillTake] = useState('');
  const [displayedProjectData, setDisplayedProjectData] = useState({});
  const navigate = useNavigate();
  const freelancerData = JSON.parse(sessionStorage.getItem('freelancerData'));
  const projectData = location.state.project;
  const isUpdate = location.state?.isUpdate;
  const bidId = location.state?.project?.id;
  let projectDataToBeTaken = JSON.parse(sessionStorage.getItem('projectDataToBeTaken'));
  let projDesc = projectDataToBeTaken?.projectDescription;

  useEffect(() => {
    if (projectData && JSON.stringify(displayedProjectData) !== JSON.stringify(projectData)) {
      setDisplayedProjectData(projectData);
    } else if (location.state?.project) {
      setDisplayedProjectData(location.state.project);
    }
    if (isUpdate) {
      setBidAmount(location.state.project.bidAmount);
      setDaysWillTake(location.state.project.daysWillTake);
      setBidDescription(location.state.project.bidDescription);
    }
  }, [projectData, isUpdate, location.state]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const freelancerId = freelancerData?.id;
    const projectId = projectData?.id;
    const bidData = {
      bidAmount,
      daysWillTake,
      bidDescription,
      projectId,
      freelancerId,
    };

    try {
      if (isUpdate) {
        await axios.put(`http://localhost:8080/bids/updateBid/${bidId}`, bidData);
      } else {
        await axios.post('http://localhost:8080/bids/postBid', bidData);
      }
      let bids = JSON.parse(localStorage.getItem('bids')) || [];
      bids.push(bidData);
      localStorage.setItem('bids', JSON.stringify(bids));
      navigate('/show-bids');
    } catch (error) {
      console.error('Error submitting bid:', error);
      alert('An error occurred while submitting your bid. Please try again.');
    }
  };

  return (
    <div className="bid-confirmation">
      <h2>Bid Confirmation</h2>
      {projectData ? (
        <div className="project-details">
          {isUpdate ? (
            <>
              <h3>Project: {projectDataToBeTaken.projectTitle}</h3>
              <p>Description: {projectDataToBeTaken.projectDescription}</p>
              <p>Current Bid Amount: {projectDataToBeTaken.bidAmount}</p>
            </>
          ) : (
            <>
              <h3>Project: {projectData.title}</h3>
              <p>Description: {projectData.description}</p>
              <p>Current Bid Amount: {projectData.budget}</p>
            </>
          )}
        </div>
      ) : (
        <p>No project details available</p>
      )}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Bid Amount</label>
          <input
            type="text"
            value={bidAmount || ''}
            onChange={(e) => setBidAmount(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Duration</label>
          <input
            type="text"
            value={daysWillTake}
            onChange={(e) => setDaysWillTake(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Bid Description</label>
          <textarea
            value={bidDescription}
            onChange={(e) => setBidDescription(e.target.value)}
          />
        </div>
        <button type="submit" className="submit-button">
          {isUpdate ? 'Update' : 'Submit'}
        </button>
      </form>
    </div>
  );
};

export default BidConfirmation;