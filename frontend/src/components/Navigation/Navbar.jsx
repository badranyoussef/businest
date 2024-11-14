import { Link, useLocation } from 'react-router-dom';
import { Briefcase, User } from 'lucide-react';
import '../../styles/Navbar.css';

export function Navbar() {
  const location = useLocation();
  
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="logo-link">
          <Briefcase className="logo-icon" />
          <span className="company-name">Businest</span>
        </Link>
        
        <Link 
          to="/profile" 
          className={`profile-link ${location.pathname === '/profile' ? 'active' : ''}`}
        >
          <User className="profile-icon" />
        </Link>
      </div>
    </nav>
  );
}
