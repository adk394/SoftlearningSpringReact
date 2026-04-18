import { Link } from 'react-router-dom';

export default function Header() {
  const navStyle = {
    backgroundColor: '#808080',
    padding: '15px 0',
    display: 'flex',
    justifyContent: 'left',
    alignItems: 'left',
    marginBottom: '20px'
  };

  const buttonStyle = {
    backgroundColor: '#c57821',
    padding: '12px 22px',
    textDecoration: 'none',
    borderRadius: '4px',
    color: 'black',
    fontSize: '16px',
    margin: '0 15px'
  };

  return (
    <nav style={navStyle}>
      <Link to="/books" style={buttonStyle}>Books</Link>
      <Link to="/clients" style={buttonStyle}>Clients</Link>
      <Link to="/orders" style={buttonStyle}>Orders</Link>
    </nav>
  );
}