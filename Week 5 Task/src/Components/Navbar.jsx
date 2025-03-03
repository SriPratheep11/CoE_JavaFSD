import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = ({ user, setUser }) => {
  const handleLogout = () => {
    localStorage.removeItem('loggedUser');
    setUser(null);
  };

  return (
    <nav className="navbar">
      <h1>SnapBuy</h1>
      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/cart">Cart</Link>
        {user ? (
          <button onClick={handleLogout}>Logout</button>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/signup">Sign Up</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
