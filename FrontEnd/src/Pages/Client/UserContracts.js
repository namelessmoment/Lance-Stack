import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../../styles/client/UserContracts.css"
import {useAuth} from "../../components/useAuth";
axios.defaults.baseURL = 'http://localhost:8080'; // Ensure base URL is set

function UserContracts() {
  useAuth();
  const [allContracts, setAllContracts] = useState([]);
  const [showCompleted, setShowCompleted] = useState(false);
  const clientData = JSON.parse(sessionStorage.getItem('clientData'));
  const freelancer = JSON.parse(sessionStorage.getItem('freelancerData'));
  console.log(freelancer)
  const userId = clientData?.id;
  console.log('UserId:', userId); // Debugging statement
  console.log(sessionStorage)
  console.log(clientData)
  const navigate = useNavigate();
  useEffect(() => {
    if (userId) {
      const fetchContracts = async () => {
        try {
          const endpoint = showCompleted ? `/contracts/completed/${userId}` : `/contracts/inProgress/${userId}`;
          const response = await axios.get(endpoint);
          setAllContracts(response.data);
          console.log('Fetched Contracts:', response.data); // Debugging statement
        } catch (error) {
          console.error('Error fetching contracts:', error.response ? error.response : error);
        }
      };
      fetchContracts();
    }
  }, [userId, showCompleted]); // Run effect when userId or showCompleted changes

  useEffect(() => {
    // Include the Razorpay checkout script
    const script = document.createElement('script');
    script.src = 'https://checkout.razorpay.com/v1/checkout.js';
    script.async = true;
    document.body.appendChild(script);
    // Cleanup script on component unmount
    return () => {
      document.body.removeChild(script);
    };
  }, []);

  const toggleContracts = () => {
    setShowCompleted(!showCompleted);
  };

  const handlePayment = async (contract) => {
    try {
      console.log(contract.id);
      const response = await axios.post('/payments/create-order', {contractId: contract.id, paymentAmount: contract.paymentAmount});
      const { razorPayOrderId } = response.data;
      console.log(response.data);
      // console.log(data)
      const options = {
        key: 'rzp_test_SdsqaXK3OqnDVp',
        amount: contract.paymentAmount * 100,
        currency: 'INR',
        name: 'Lance Stack',
        description: 'Test Transaction',
        order_id: razorPayOrderId,
        callback_url:`http://localhost:8080/payments/handle-payment-callback-success?razor_pay_order_id=${razorPayOrderId}&contractId=${contract.id}`,
        prefill: {
          name: contract.freelancer.freelancerName,
          email: contract.freelancer.email,
          contact: contract.freelancer.mobileNumber,
          // razor_pay_order_id: razorPayOrderId.razorPayOrderId
        },
        notes: {
          address: 'Razorpay Corporate Office',
        },
        theme: {
          color: '#3399cc',
        },
      };
      const rzp1 = new window.Razorpay(options);
      rzp1.open();
      // rzp1.then((response) => {
          // navigate('http://localhost:8080/user-contracts'); // Navigate to user contracts on successful payment
      // });
    } catch (error) {
      console.error('Error creating order:', error);
      // Handle error (e.g., show error message)
    }
  };
    // window.location.href = '/user-contracts'; // Navigate to user contracts on successful payment
    // useNavigate('/user-contracts');
  if (!userId) {
    return <div>Error: User data not found. Please log in again.</div>;
  }

  const contractsToShow = showCompleted ? allContracts.filter(contract => contract.status === 'COMPLETED') : allContracts.filter(contract => contract.status === 'IN_PROGRESS');

  return (
    <div className="user-contracts">
      <h2>My Contracts</h2>
      <div className="toggle-buttons">
        <button onClick={toggleContracts}>
          {showCompleted ? 'Show Pending Contracts' : 'Show Completed Contracts'}
        </button>
      </div>
      <div className="contract-cards">
        {contractsToShow.map((contract) => (
          <div key={contract.project.id} className="contract-card">
            <h3>{contract.project.title}</h3>
            <p>Freelancer: {contract.freelancer.freelancerName}</p>
            {/* Add Freelancer Name */}
            <p>Amount: ₹{contract.paymentAmount}</p>
            <p>Contract Status: {contract.status}</p>
            <p>Project Status: {contract.project.status}</p>
            {contract.status === 'IN_PROGRESS' && (
              <button onClick={() => handlePayment(contract)}>Pay Now</button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default UserContracts;      