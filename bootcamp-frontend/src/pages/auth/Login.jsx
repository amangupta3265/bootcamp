import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const Login = () => {

    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        const username = e.target[0].value;
        const password = e.target[1].value;

        console.log(username, password)

        axios.post('http://localhost:3000/auth/login', {
            "email": username,
            "password": password
        },{
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'http://localhost:3000'
              }
        }).then((res) => {
                //setting csrf login
                console.log("Token",res.data.token)
                localStorage.setItem('token', res.data.token)
                navigate('/home')
        }).catch(() => {
            alert('Error occured while logging you in. Try again')
        })
    }

    return ( 
        <div >
            <form style={{"display": "flex", "flex-direction": "column"}} onSubmit={handleLogin}>
                <label style={{"padding": "5px"}}>
                    Username:
                    <input type="text" />
                </label>
                <label style={{"padding": "5px"}}>
                    Password:
                    <input type="password"/>
                </label>
                <button style={{"padding": "5px" ,"textAlign" : "center" ,"width" :"auto" }} type="Submit">Login</button>
                <span style={{"padding": "5px"}}>
                    New user?
                    <Link to='/register'>Register</Link>
                </span>
                <Link to='/forgotPassword'>Forgot Password</Link>
            </form>
        </div>
     );
}
 
export default Login;