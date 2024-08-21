import React, { useEffect } from 'react';
import { useAuth } from '../../components/useAuth';

function PaymentComponent() {
  useAuth();
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

  const handlePayment = async () => {
    try {
      // Create order on the backend
      const response = await fetch('http://localhost:8080/payments/create-order', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ contractId: 5, amount: 50000 }), // Send the amount to the backend
      });

      const orderData = await response.json();

      const options = {
        key: 'rzp_test_SdsqaXK3OqnDVp',
        amount: '50000',
        currency: 'INR',
        name: 'Acme Corp',
        description: 'Test Transaction',
        image: 'https://example.com/your_logo',
        order_id: orderData.orderId, // Use the order ID from the backend
        callback_url: 'http://localhost:8080/payments/handle-payment-callback',
        prefill: {
          name: 'Gaurav Kumar',
          email: 'gaurav.kumar@example.com',
          contact: '8999257171',
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
    } catch (error) {
      console.error('Error creating order:', error);
    }
  };

  return (
    <div>
      <button onClick={handlePayment}>Pay</button>
    </div>
  );
}

export default PaymentComponent;