import React from 'react';
import { Link } from 'react-router-dom';
import "../styles/freelancer/Navbar.css"
const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="navbar-list">
        <li className="navbar-item">
          <Link to="/freelancer">Home</Link>
        </li>
        <li className="navbar-item">
          <Link to="/my-projects">My Projects</Link>
        </li>
        <li className="navbar-item">
          <Link to="/contracts">Contracts</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
