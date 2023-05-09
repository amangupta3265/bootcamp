import axios from "axios";
import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

const OtpEmail = () => {

    const [email,setEmail]=useState('')
    const navigate = useNavigate();

    const sendMail =()=>{
        console.log(email)
        axios.post('http://localhost:8080/sendMail', {
            "email": email,
        }).then((res) => {
                navigate('/otp')
        }).catch(() => {
            alert('Error occured. Try again')
        })
    }

    const emailRef=useRef('')

    const handleEmail=(e)=>{
        //console.log(emailRef.current.value)
        setEmail(emailRef.current.value);
    }

    return ( 
        <form style={{"display": "flex", "flex-direction": "column"}} onSubmit={sendMail}>
            <label>
                Email:
                <input value={email} ref={emailRef} type="text" onChange={handleEmail} />
            </label>
            <button>Next</button>
        </form>
     );
}
 
export default OtpEmail;