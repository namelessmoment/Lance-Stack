
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Fl from '../../components/Fl';
import "../../styles/client/clientpage.css";
import Navigation from "../../components/Navigation";
import { useAuth } from '../../components/useAuth';

export default function FreelancerProfile() {
  useAuth();
  const [freelancers, setFreelancers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch freelancers data from API
    axios.get('http://localhost:8080/freelancers')
      .then(response => {
        setFreelancers(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching freelancers:', error);
        setError('Failed to fetch freelancers.');
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p className="text-danger">{error}</p>;

  return (
    <div className="freelancers">
      <Navigation />
      <div className="cards">
        {freelancers.map(freelancer => (
          <Fl
            key={freelancer.id}
            name={freelancer.freelancerName} // Adjusted field
            email={freelancer.email}
            skills={freelancer.skills}
            description={freelancer.profileDescription} // Adjusted field
          />
        ))}
      </div>
    </div>
  );
}
