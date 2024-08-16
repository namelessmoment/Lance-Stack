import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import '../../styles/freelancer/MyProjects.css';

const MyProjects = () => {
  const navigate = useNavigate();
  const [inProgressProjects, setInProgressProjects] = useState([]);
  const [completedProjects, setCompletedProjects] = useState([]);
  const freelancerId = JSON.parse(sessionStorage.getItem("freelancerData")).id; // Assuming freelancerData is stored in session storage
  const freelancerData = JSON.parse(sessionStorage.getItem("freelancerData"));
  
  // Fetch in-progress and completed projects when the component loads
  useEffect(() => {
    fetchInProgressProjects();
    fetchCompletedProjects();
  }, []);

  const fetchInProgressProjects = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/freelancers/${freelancerId}/allocatedIn_Progress`);
      setInProgressProjects(response.data);
    } catch (error) {
      console.error('Error fetching in-progress projects:', error);
    }
  };

  const fetchCompletedProjects = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/freelancers/${freelancerId}/allocated_Completed`);
      setCompletedProjects(response.data);
    } catch (error) {
      console.error('Error fetching completed projects:', error);
    }
  };

  const handleMarkAsComplete = async (projectId) => {
    try {
      await axios.patch(`http://localhost:8080/freelancers/projects/complete/${projectId}`);
      
      // Update the frontend state after marking the project as complete
      const projectToMark = inProgressProjects.find(project => project.id === projectId);
      if (projectToMark) {
        setInProgressProjects(inProgressProjects.filter(project => project.id !== projectId));
        setCompletedProjects([...completedProjects, projectToMark]);
      }
    } catch (error) {
      console.error('Error marking project as complete:', error);
    }
  };

  const handleCompletedProjectsClick = () => {
    navigate('/completed-projects', { state: { completedProjects } });
  };

  return (
    <div className="my-projects">
      <div className="header">
        <div className="freelancer-name">{freelancerData.freelancerName}</div>
        <div className="navbar">
        <Link to="/freelancer">Home</Link>
          <Link to="/contracts">Contracts</Link>
          <Link to="/show-bids">Show Bids</Link>
        </div>
      </div>
      <div className="projects-section">
        <div className="in-progress-section">
          <h2>In Progress Projects</h2>
          <div className="project-list">
            {inProgressProjects.map((project) => (
              <div className="project-card" key={project.id}>
                <h3>{project.title}</h3>
                <p>{project.description}</p>
                <button className="complete-button" onClick={() => handleMarkAsComplete(project.id)}>Mark as Complete</button>
              </div>
            ))}
          </div>
        </div>
        <button className="completed-projects-button" onClick={handleCompletedProjectsClick}>
          Go to Completed Projects
        </button>
      </div>
    </div>
  );
};

export default MyProjects;
