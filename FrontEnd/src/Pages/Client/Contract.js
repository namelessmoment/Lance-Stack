import React from "react";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "../../styles/client/contract.css";

const contract = {
  projectTitle: "Website Development",
  freelancerName: "John Doe",
  freelancerProfileDescription: "Experienced web developer with expertise in React and Node.js",
  freelancerEmail: "john.doe@example.com",
  startDate: "2024-08-01",
  endDate: "2024-09-01",
};
const ContractDetails = () => {
  const [freelancer, setFreelancer] = useState({});
  const [bid, setBid] = useState({});
  const [project, setProject] = useState({});
  const [isChecked, setIsChecked] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState("");
  const navigate = useNavigate();
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const projectData = JSON.parse(sessionStorage.getItem("selectedProject"));
        const bidData = JSON.parse(sessionStorage.getItem("selectedBid"));
        // sessionStorage.setItem()
        console.log(sessionStorage)
        if (projectData && bidData) {
        setProject(projectData);
        setBid(bidData);

        // console.log(sessionStorage)
        console.log(projectData)
        console.log(bidData)

        // Fetch freelancer details
        const response = await axios.get(`http://localhost:8080/bids/getFreelancerByBidId/${bidData.id}`);
        setFreelancer(response.data);
        console.log(response.data)
        }
        
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setError("Failed to fetch data. Please try again.");
        setLoading(false);
      }
    };

    fetchData();
  }, []);
  

  const startDate = new Date().toISOString().split('T')[0]; // Current date
  const endDate = bid.daysWillTake
    ? new Date(new Date().getTime() + (bid.daysWillTake * 24 * 60 * 60 * 1000)).toISOString().split('T')[0]
    : "N/A"; // Default to "N/A" if daysWillTake is not available
  const handleCheckboxChange = (e) => {
    setIsChecked(e.target.checked);
  };

  const handleCreateContract = async () => {
    if (isChecked) {
      try {
        const contractDTO = {
          startDate: startDate,
          endDate: endDate,
          paymentAmount: bid.bidAmount,
          projectId: project.id,
          freelancerId: freelancer.id
        };

        console.log(contractDTO);

        const response = await axios.post('http://localhost:8080/contracts/createContract', contractDTO);

        if (response.status === 201) {
          setSuccess("Contract Created Successfully!");
          // Optionally, redirect or clear form here
          navigate('/user-contracts')
        } else {
          setError("Failed to create contract. Please try again.");
        }
      } catch (error) {
        setError("An error occurred while creating the contract.");
      }
    } else {
      alert("Please accept the terms and conditions.");
    }
  };

  return (
    <div className="container">
      <h2 className="text-center">Contract Details</h2>
      <div className="card1">
        <div className="card-body">
          <h5 className="card-title">Project Title</h5>
          <p className="card-text">{project.title}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">Freelancer Name</h5>
          <p className="card-text">{freelancer.freelancerName}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">Freelancer Profile Description</h5>
          <p className="card-text">{freelancer.profileDescription}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">Freelancer Email</h5>
          <p className="card-text">{freelancer.email}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">Bid Amount</h5>
          <p className="card-text">{bid.bidAmount}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">Start Date</h5>
          <p className="card-text">{startDate}</p>
        </div>
        <div className="card-body">
          <h5 className="card-title">End Date</h5>
          <p className="card-text">{endDate}</p>
        </div>
      </div>
      <div className="card2">
        <div className="card-body">
          <h5 className="card-title">Terms and Conditions</h5>
          <p className="card-text">
            1. The freelancer agrees to complete the project by the specified end
            date. <br />
            2. The client agrees to pay the bid amount upon successful completion
            of the project. <br />
            3. Any modifications to the project scope must be agreed upon by both
            parties. <br />
            4. Confidentiality must be maintained by both parties regarding
            project details. <br />
            5. The freelancer guarantees that all work will be original and free
            from plagiarism. <br />
            6. The client has the right to request progress updates at any time.
            <br />
            7. Any disputes will be resolved through mutual negotiation. <br />
            8. Both parties agree to the terms and conditions set forth in this
            contract.
          </p>
        </div>
      </div>
      <div className="form-check">
        <input
          type="checkbox"
          className="form-check-input"
          id="termsConditions"
          checked={isChecked}
          onChange={handleCheckboxChange}
        />
        <label className="form-check-label" htmlFor="termsConditions">
          I agree to the terms and conditions
        </label>
      </div>
      <button
        className="btn btn-primary"
        onClick={handleCreateContract}
        disabled={!isChecked}
      >
        Create Contract
      </button>
    </div>
  );
};

export default ContractDetails;