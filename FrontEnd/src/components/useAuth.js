import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const useAuth = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const clientData = sessionStorage.getItem('clientData');
    const freelancerData = sessionStorage.getItem('freelancerData');

    if (!clientData && !freelancerData) {
      navigate('/login');
    }
  }, [navigate]);
};
