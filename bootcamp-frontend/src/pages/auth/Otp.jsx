import { useNavigate } from "react-router-dom";

const Otp = () => {
    const navigate = useNavigate();

    return ( 
        <form onSubmit={() => {navigate('/resetPassword')}}>
            <label>
                OTP:
                <input type="text" />
            </label>
            <button type="submit">Next</button>
        </form>
     );
}

export default Otp;