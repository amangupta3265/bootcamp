import "./navbar.scss";
import { Link } from "react-router-dom";
const Navbar = () => {
  
  return (
    <div className="navbar">
      <div className="container">
        <div className="left">
          <Link to="/home" className="link">
            <span>Home</span>
          </Link>
          <Link to="/notes" className="link">
            <span>MyNotes</span>
          </Link>
          <Link to="/sharedNotes" className="link">
            <span>Shared Notes</span>
          </Link>
          <Link to="/publishedNotes" className="link">
            <span>Published Notes</span>
          </Link>
        </div>
        <div className="right">
            <Link to="/profile">
            <img
                src="https://images.pexels.com/photos/6899260/pexels-photo-6899260.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                alt=""
            />
            </Link>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
