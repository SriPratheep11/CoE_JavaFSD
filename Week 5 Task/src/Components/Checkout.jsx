import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Checkout.css'; // New CSS file for better styling

const Checkout = ({ user }) => {
  const [showPopup, setShowPopup] = useState(false);
  const navigate = useNavigate();

  const handleOrder = () => {
    setShowPopup(true);
    localStorage.removeItem('cart');
    setTimeout(() => {
      navigate('/');
    }, 3000); // Redirect after 3 seconds
  };

  return (
    <div className="checkout-container">
      <h2>Confirm Your Order</h2>
      <div className="order-summary">
        <p><strong>Name:</strong> {user?.name}</p>
        <p><strong>Address:</strong> {"sripratheep Home"}</p>
        <p><strong>Phone:</strong> {user?.phone}</p>
        <button className="place-order-btn" onClick={handleOrder}>Place Order</button>
      </div>

      {showPopup && (
        <div className="order-popup">
          <div className="popup-content">
            <h2>🎉 Thank You for Shopping!</h2>
            <p>Order placed for <strong>{user?.name}</strong></p>
            <p>📍 Delivered to: <strong>{"sripratheep Home"}</strong></p>
            <p>📞 Contact: <strong>{user?.phone}</strong></p>
          </div>
        </div>
      )}
    </div>
  );
};

export default Checkout;
