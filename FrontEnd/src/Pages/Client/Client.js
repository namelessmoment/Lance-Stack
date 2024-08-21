import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import "../../styles/client/clientpage.css";
import Navigation from "../../components/Navigation";
import { useAuth } from "../../components/useAuth";

export default function ClientPage() {
  useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    // Check if the user is authenticated
    const clientData = sessionStorage.getItem('clientData');
    if (!clientData) {
      // Redirect to login if not authenticated
      navigate('/login');
    }
  }, [navigate]);

  console.log(sessionStorage);

  return (
    <div className="clienthome">
      <Navigation/>
      <div className="fl">
        <br/>
        <h3>
          Welcome back to LanceStack, your go-to platform for tech project freelancing! We’re thrilled to see you again. Here’s a quick snapshot of your recent activities:
        </h3>
        <h3>Let’s get started! Whether you're looking to kick off a new project or check on existing ones, everything you need is right at your fingertips.</h3>
        {/* 
          Optionally, you can add dynamic content here, such as the number of active projects, by fetching data from an API or state:
          <h4>Active Projects: {activeProjects}</h4>
        */}
      </div>
    </div>
  );
}
