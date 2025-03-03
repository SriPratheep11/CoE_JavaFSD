import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState } from 'react';
import Home from './Components/Home';
import Login from './Components/Login';
import Signup from './Components/Signup';
import Cart from './Components/Cart';
import Checkout from './Components/Checkout';
import Navbar from './Components/Navbar';

function App() {
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('loggedUser')) || null);

  return (
    <Router>
      <Navbar user={user} setUser={setUser} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login setUser={setUser} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/Cart" element={<Cart/>}/>
        <Route path="/checkout" element={<Checkout user={user} />} />
      </Routes>
    </Router>
  );
}

export default App;
