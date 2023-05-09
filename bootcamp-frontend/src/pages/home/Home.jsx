import { Link } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";

const Home = () => {
    return ( 
        <>
            <Navbar/>
            <div style={{"display": "flex", "flex-direction": "column"}}>
            <Link to='/profile'>User Profile</Link>
            <Link to='/notes'>Create new note</Link>
            <Link to='/notes'>Show My notes</Link>
            </div>
        </>
        
     );
}
 
export default Home;