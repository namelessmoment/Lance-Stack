import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/freelancer/Freelancer.css';
import ProfilePanel from './ProfilePanel';
import axios from 'axios';

const Freelancer = () => {
  const [freelancerName, setFreelancerName] = useState('');
  const [showFilters, setShowFilters] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [projects, setProjects] = useState([]);
  const [filteredProjects, setFilteredProjects] = useState([]);
  const [bidAmountRange, setBidAmountRange] = useState([0, 100]);
  const [selectedTypes, setSelectedTypes] = useState([]);
  const [showProfilePanel, setShowProfilePanel] = useState(false);

  const navigate = useNavigate();
  const filterPanelRef = useRef(null);
  const profilePanelRef = useRef(null);

  useEffect(() => {
    const freelancerData = sessionStorage.getItem('freelancerData');
    if (freelancerData) {
      const parsedData = JSON.parse(freelancerData);
      setFreelancerName(parsedData.freelancerName);
    }
    axios.get('http://localhost:8080/projects')
      .then(response => {
        setProjects(response.data);
        setFilteredProjects(response.data);
      })
      .catch(error => {
        console.error('Error fetching projects:', error);
      });
  }, []);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (filterPanelRef.current && !filterPanelRef.current.contains(event.target)) {
        setShowFilters(false);
      }
      if (profilePanelRef.current && !profilePanelRef.current.contains(event.target)) {
        setShowProfilePanel(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const toggleFilters = () => {
    setShowFilters(!showFilters);
  };

  const handleTypeChange = (e) => {
    const value = e.target.value;
    setSelectedTypes(
      e.target.checked 
        ? [...selectedTypes, value] 
        : selectedTypes.filter(type => type !== value)
    );
  };

  const applyFilters = () => {
    let filtered = projects;

    if (selectedTypes.length > 0) {
      filtered = filtered.filter(project => selectedTypes.includes(project.type));
    }
    filtered = filtered.filter(project => project.bidAmount >= bidAmountRange[0] && project.bidAmount <= bidAmountRange[1]);

    if (searchQuery) {
      filtered = filtered.filter(project => project.title.toLowerCase().includes(searchQuery.toLowerCase()));
    }

    setFilteredProjects(filtered);
  };

  const handleSearchChange = (e) => {
    const query = e.target.value.toLowerCase();
    setSearchQuery(query);
    const filtered = projects.filter(project =>
      project.title.toLowerCase().includes(query) ||
      project.description.toLowerCase().includes(query)
    );
    setFilteredProjects(filtered);
  };

  const handleBidClick = (project) => {
    sessionStorage.setItem('projectData', JSON.stringify(project));
    navigate('/bid-confirmation', {
      state: { project }
    });
  };

  const goToMyProjects = () => {
    navigate('/my-projects');
  };

  const goToShowBids = () => {
    navigate('/show-bids');
  };

  const goToContracts = () => {
    navigate('/contracts');
  };

  const toggleProfilePanel = () => {
    setShowProfilePanel(!showProfilePanel);
  };

  const handleBidAmountChange = (e) => {
    const value = parseInt(e.target.value, 10);
    setBidAmountRange([0, value]);
  };

  return (
    <div className="freelancer">
      <header className="header">
        <div className="freelancer-name">Hello, {freelancerName}</div>
        <nav className="navbar">
          <button onClick={goToMyProjects}>My Projects</button>
          <button onClick={goToShowBids}>Show Bids</button>
          <button onClick={goToContracts}>Contracts</button>
          <button onClick={toggleProfilePanel}>Profile</button>
        </nav>
      </header>
      <div className="content">
        <div className="search-container">
          <input 
            type="text" 
            className="search-bar" 
            placeholder="Search for projects..." 
            value={searchQuery}
            onChange={handleSearchChange}
          />
        </div>
        <button className="filter-button" onClick={toggleFilters}>Filter</button>
        <div className={`filter-section ${showFilters ? 'open' : ''}`} ref={filterPanelRef}>
          <div className="filter-header">
            <button className="close-button" onClick={toggleFilters}>×</button>
          </div>
          {showFilters && (
            <div className="filters">
              <h3>Filters</h3>
              <div className="filter-group">
                <h4>By Bid Amount</h4>
                <input 
                  type="range" 
                  min="0" 
                  max="100" 
                  value={bidAmountRange[1]} 
                  onChange={handleBidAmountChange} 
                />
                <div>Range: {bidAmountRange[0]} - {bidAmountRange[1]}</div>
              </div>
              <div className="filter-group">
                <h4>By Project Type</h4>
                <label>
                  <input type="checkbox" value="Web_Application" onChange={handleTypeChange} />
                  Web Application
                </label>
                <label>
                  <input type="checkbox" value="AndroidApplication" onChange={handleTypeChange} />
                  Android Application
                </label>
                <label>
                  <input type="checkbox" value="DesktopApplication" onChange={handleTypeChange} />
                  Desktop Application
                </label>
                <label>
                  <input type="checkbox" value="GameDevelopment" onChange={handleTypeChange} />
                  Game Development
                </label>
                <label>
                  <input type="checkbox" value="EnterpriseSoftware" onChange={handleTypeChange} />
                  Enterprise Software
                </label>
              </div>
              <button onClick={applyFilters}>Apply Filters</button>
            </div>
          )}
        </div>
        <div className="project-list">
          {filteredProjects.map(project => (
            <div key={project.id} className="project-card">
              <h3>{project.title}</h3>
              <p>Type: {project.projType}</p>
              <p>Bid Amount: ${project.budget}</p>
              <button className="bid-button" onClick={() => handleBidClick(project)}>Bid</button>
            </div>
          ))}
        </div>
      </div>
      {showProfilePanel && (
        <div className="profile-panel-container" ref={profilePanelRef}>
          <ProfilePanel onClose={toggleProfilePanel} />
        </div>
      )}
      <footer className="footer">
        <p>All rights reserved &copy; {new Date().getFullYear()}</p>
      </footer>
    </div>
  );
};

export default Freelancer;
