import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/freelancer/Contracts.css';
import axios from 'axios';

const Contracts = () => {
  const [inProcessContracts, setInProcessContracts] = useState([]);
  const [completedContracts, setCompletedContracts] = useState([]);
  const [showCompleted, setShowCompleted] = useState(false);
  const freelancerId = JSON.parse(sessionStorage.getItem("freelancerData")).id; // Assuming you store freelancer data in session storage
  
  useEffect(() => {
    fetchInProcessContracts();
  }, []);

  const fetchInProcessContracts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/contracts/${freelancerId}/getAllInProgressContractsByFreelancer`);
      setInProcessContracts(response.data);
    } catch (error) {
      console.error('Error fetching in-process contracts', error);
    }
  };

  const fetchCompletedContracts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/contracts/${freelancerId}/getAllCompletedContractsByFreelancer`);
      setCompletedContracts(response.data);
    } catch (error) {
      console.error('Error fetching completed contracts', error);
    }
  };

  const toggleCompletedContracts = () => {
    if (!showCompleted) {
      fetchCompletedContracts();
    }
    setShowCompleted(!showCompleted);
  };

  return (
    <div className="contracts">
      <div className="header">
        <div className="freelancer-name"></div>
        <div className="navbar">
          <Link to="/freelancer">Home</Link>
          <Link to="/my-projects">My Projects</Link>
          <Link to="/show-bids">Show Bids</Link>
        </div>
      </div>
      <h2>In-Process Contracts</h2>
      {inProcessContracts.map(contract => (
        <div key={contract.id} className="contract-card">
          <h3>{contract.project.title}</h3>
          <p>Description: {contract.project.description}</p>
          <p>Start Date: {contract.startDate}</p>
          <p>End Date: {contract.endDate}</p>
          <p>Status: {contract.status}</p>
          <p>Amount: {contract.paymentAmount}</p>
        </div>
      ))}
      <button className="toggle-button" onClick={toggleCompletedContracts}>
        {showCompleted ? "Hide" : "Show"} Completed Contracts
      </button>
      {showCompleted && (
        <div className="completed-contracts">
          <h2>Completed Contracts</h2>
          {completedContracts.map(contract => (
            <div key={contract.id} className="contract-card">
              <h3>{contract.project.title}</h3>
              <p>Description: {contract.project.description}</p>
              <p>Start Date: {contract.startDate}</p>
              <p>End Date: {contract.endDate}</p>
              <p>Status: {contract.status}</p>
              <p>Amount: {contract.paymentAmount}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Contracts;
