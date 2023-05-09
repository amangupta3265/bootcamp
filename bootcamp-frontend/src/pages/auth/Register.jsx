import axios from "axios";
import { useRef } from "react";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {

    const navigate = useNavigate()
    const imageButtonRef = useRef();


    const handleRegister = (e) => {
        e.preventDefault();
        const username = e.target[0].value;
        const name = e.target[1].value;
        const password = e.target[2].value;

        axios.post('http://localhost:3000/signup', {
            "email": username,
            "name" : name,
            "password" :password
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': 'http://localhost:3000'
              }
        }).then((res) => {
            localStorage.setItem('token', res.data.token)
            console.log(res.data.token)
            // if (imageButtonRef.current.files.size !== 0)
            //     axios.post('http://localhost:8080/image', imageButtonRef.current.files[0], {
            //         headers: {
            //             Authorization: `Bearer ${res.data.token}`
            //         }
            // }).then(() => navigate('/home')) 
            // else          
                navigate('/home')
        }).catch(() => alert('error while registering user, try again later'))
    }
    
    return ( 
        <div>
            <form style={{"display": "flex", "flex-direction": "column"}} onSubmit={handleRegister}>
                <label style={{"padding": "5px"}}>
                    Email:
                    <input type="text" />
                </label>
                <label style={{"padding": "5px"}}>
                    Name:
                    <input type="text" />
                </label>
                <label style={{"padding": "5px"}}>
                    Password:
                    <input type="password" />
                </label>
                <div>
                    <input ref={imageButtonRef} type="file" accept="image/png, image/gif, image/jpeg" onChange={() => {}} />
                    <button onClick={(e) => {e.preventDefault(); console.log(imageButtonRef.current.files[0])}}>Upload</button>
                </div>
                <button type="submit" style={{"padding": "5px"}}>Register</button>
                <Link style={{"padding": "5px"}} to={'/login'}>Go to login page</Link>
            </form>
        </div>
     );
}
 
export default Register;