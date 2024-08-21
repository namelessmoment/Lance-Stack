import React, { useState, useEffect } from "react";
import axios from "axios";
import "../../styles/client/clientpage.css";
import Navigation from "../../components/Navigation";
import { Link } from "react-router-dom";
import {useAuth} from "../../components/useAuth";
export default function Projects() {
  useAuth();
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const clientId= JSON.parse(sessionStorage.getItem("clientData")).id; // Assuming freelancerData is stored in session storage
  console.log(clientId);
  useEffect(() => {
    // Fetch project data from API
    axios.get(`http://localhost:8080/projects/user/${clientId}/projects`)
      .then(response => {
        // Fetch project data from API
        setProjects(response.data.filter(project => project.status === "PENDING"));
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching projects:', error);
        setError('Failed to fetch projects.');
        setLoading(false);
      });
  }, [clientId]);

  const handleShowBidsClick = (project) => {
    sessionStorage.setItem("selectedProject", JSON.stringify(project));
  };
  console.log(sessionStorage)
  if (loading) return <p>Loading...</p>;
  if (error) return <p className="text-danger">{error}</p>;

  return (
    <div className="projects">
      <Navigation />
      <Link to={'/PostProject'} className="btn">
        <button>Post Project</button>
      </Link>
      <div className="cards">
        {projects.map(project => (
          <div key={project.id} className="card">
            <div className="cardbody">
              <h4>{project.title}</h4>
              <h4>{project.description}</h4>
              <h4>Status: {project.status}</h4>
              {/* Conditionally render buttons based on project status */}
              {/* {project.status === "PENDING" ? ( */}
                <Link to={`/bids/${project.id}`}>
                  <button
                    onClick={() => handleShowBidsClick(project)}
                  >
                    Show Bids
                  </button>
                </Link>
              {/* ) : (
                <Link to={`/contract/${project.id}/${project.bidId}`}>
                  <button>Show Contract</button>
                </Link>
              )} */}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
