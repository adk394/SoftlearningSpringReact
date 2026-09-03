import { NavLink, Link } from 'react-router-dom';

export default function Header() {
  return (
    <header className="topbar">
      <Link className="brand" to="/"><span className="brand-mark">B</span><span>Bookora</span></Link>
      <nav className="nav-links" aria-label="Main navigation">
        <NavLink className="nav-link" to="/books">Books</NavLink>
        <NavLink className="nav-link" to="/clients">Clients</NavLink>
        <NavLink className="nav-link" to="/orders">Orders</NavLink>
      </nav>
      <div className="profile-chip" aria-label="Current workspace"><span className="status-dot" /><span>Workspace</span></div>
    </header>
  );
}