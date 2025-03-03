import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Cart.css'; // New CSS file for better styling

const Cart = () => {
  const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);
  const navigate = useNavigate();

  const removeFromCart = (index) => {
    const updatedCart = cart.filter((_, i) => i !== index);
    setCart(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  };

  const totalAmount = cart.reduce((total, item) => total + item.price, 0);

  return (
    <div className="cart-container">
      <h2>🛒 Your Shopping Cart</h2>
      {cart.length === 0 ? (
        <p className="empty-cart">Your cart is empty.</p>
      ) : (
        <div className="cart-items">
          {cart.map((item, index) => (
            <div key={index} className="cart-item">
              <img src={item.image} alt={item.name} />
              <div className="cart-details">
                <h4>{item.name}</h4>
                <p>₹{item.price}</p>
                <button className="remove-btn" onClick={() => removeFromCart(index)}>❌ Remove</button>
              </div>
            </div>
          ))}
        </div>
      )}
      <div className="cart-footer">
        <h3>Total: ₹{totalAmount}</h3>
        {cart.length > 0 && <button className="checkout-btn" onClick={() => navigate('/checkout')}>Proceed to Buy</button>}
      </div>
    </div>
  );
};

export default Cart;
