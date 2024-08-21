import React, { useEffect, useState } from 'react';
import '../../styles/freelancer/ShowBids.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; 
import Navbar from '../../components/Navbar';

const ShowBids = () => {
  const [bids, setBids] = useState([]);
  const freelancerId = JSON.parse(sessionStorage.getItem('freelancerData'))?.id;
  console.log(sessionStorage);
  let bids1 = JSON.parse(sessionStorage.getItem('bids')) || [];
  let projectData = JSON.parse(sessionStorage.getItem('projectData')) || [];
  console.log(projectData);
  const navigate = useNavigate();

  useEffect(() => {
    if (freelancerId) {
      axios.get(`http://localhost:8080/bids/getByFreelancerId/${freelancerId}`)
        .then(response => {
          const fetchedBids = response.data;
          console.log(bids)
          const bidPromises = fetchedBids.map(bid => 
            axios.get(`http://localhost:8080/projects/projectTitle/${bid.projectId}`)
              .then(projectResponse => ({
                ...bid,
                projectTitle: projectResponse.data
              }))
          );

          Promise.all(bidPromises)
            .then(updatedBids => {
              setBids(updatedBids);
            })
            .catch(error => {
              console.error('Error fetching project titles:', error);
            });
        })
        .catch(error => {
          console.error('Error fetching bids:', error);
        });
    }
  }, [freelancerId]);

  const handleUpdateClick = (bid) => {
    const projectId = bid.projectId;
    axios.get(`http://localhost:8080/projects/projectDesc/${projectId}`)
      .then(response => {
        const projectDescription = response.data;
        const projectDataToBeTaken = {
          projectTitle : bid.projectTitle,
          bidAmount : bid.bidAmount,
          projectDescription
        };

        console.log(projectDataToBeTaken)
        sessionStorage.setItem('projectDataToBeTaken', JSON.stringify(projectDataToBeTaken));
        navigate('/bid-confirmation', { state: { project: bid, isUpdate: true } });
      })
      .catch(error => {
        console.error('Error fetching project description:', error);
      });
  };

  const handleDiscardClick = (bidId) => {
    axios.delete(`http://localhost:8080/bids/deleteBid/${bidId}`)
      .then(response => {
        setBids(bids.filter(bid => bid.id !== bidId));
      })
      .catch(error => {
        console.error('Error deleting bid:', error);
      });
  };

  return (
    <div>
      <Navbar /> {/* Include Navbar component */}
      <div className="show-bids">
        <h2>Your Bids</h2>
        <div className="bids-container">
          {bids.length > 0 ? (
            bids.map((bid, index) => (
              <div key={index} className="bid-card">
                <h3>Project: {bid.projectTitle || 'N/A'}</h3>
                <p>Bid Amount: ${bid.bidAmount}</p>
                <button className="update-button" onClick={() => handleUpdateClick(bid)}>Update</button>
                <button className="discard-button" onClick={() => handleDiscardClick(bid.id)}>Discard</button>
              </div>
            ))
          ) : (
            <p>No bids found</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ShowBids;
