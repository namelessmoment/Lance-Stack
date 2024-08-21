import React, { useState, useEffect, useCallback } from "react";
import { useParams, Link ,useNavigate} from "react-router-dom"; // Import useParams and Link
import { useAuth } from "../../components/useAuth";
import '../../styles/client/bids.css';

const BidList = () => {
  useAuth();
  const { projectId } = useParams(); // Get projectId from URL parameters
  const [bids, setBids] = useState([]);
  const navigate = useNavigate();

  console.log(bids);
  
  // Memoize the fetchBids function to avoid re-creation on every render
  const fetchBids = useCallback(async () => {
    try {
      const response = await fetch(`http://localhost:8080/bids/project/${projectId}`);
      const data = await response.json();
      setBids(data);
      // console.log(response.body);
    } catch (error) {
      console.error("Failed to fetch bids:", error);
    }
  }, [projectId]);  // Dependency array ensures fetchBids only changes when projectId changes

  // Fetch bids when the component mounts or when projectId changes
  useEffect(() => {
    if (projectId) {
      fetchBids();
    }
  }, [projectId, fetchBids]);  // Add fetchBids to the dependency array

  const handleAssign = (bid) => {
    // Navigate to the next page with bid data
    sessionStorage.setItem("selectedBid", JSON.stringify(bid));
    navigate(`/contract/${projectId}/${bid.id}`);
  };

  return (
    <div className="bid-list">
      <h2>Bids for the Project</h2>
      {bids.length > 0 ? (
        <ul className="list-group">
          {bids.map((bid) => (
            <li key={bid.id} className="list-group-item">
              <div className="bid-info">
                <h5>{bid.freelancerName}</h5>
                <p>Bid Amount: ₹{bid.bidAmount}</p>
                <p>Description: {bid.bidDescription}</p>
                <p>Days to Complete: {bid.daysWillTake}</p>
                <Link to={`/contract/${projectId}/${bid.id}`}>
                  <button onClick={() => handleAssign(bid)}>Assign</button>
                </Link>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p>No bids have been placed yet.</p>
      )}
    </div>
  );
};

export default BidList;
