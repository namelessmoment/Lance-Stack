import React from 'react';
import { useLocation } from 'react-router-dom';
import '../../styles/freelancer/CompletedProjects.css';

const CompletedProjects = () => {
  const location = useLocation();
  const completedProjects = location.state?.completedProjects || [];

  return (
    <div className="completed-projects">
      <h2>Completed Projects</h2>
      <div className="project-list">
        {completedProjects.map((project) => (
          <div className="project-card" key={project.id}>
            <h3>{project.title}</h3>
            <p>{project.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CompletedProjects;
