import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css'; // Add a CSS file for better styling

const Login = ({ setUser }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const users = JSON.parse(localStorage.getItem('users')) || [];
    const validUser = users.find((user) => user.username === formData.username && user.password === formData.password);

    if (validUser) {
      localStorage.setItem('loggedUser', JSON.stringify(validUser));
      setUser(validUser);
      navigate('/');
    } else {
      alert('Invalid username or password');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="username" placeholder="Username" value={formData.username} onChange={handleChange} required />
          <input type="password" name="password" placeholder="Password" value={formData.password} onChange={handleChange} required />
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
