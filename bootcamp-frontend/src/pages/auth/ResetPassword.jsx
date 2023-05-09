import { useNavigate } from "react-router-dom";

const ResetPassword = () => {

    const navigate = useNavigate();

    return ( 
        <form style={{"display":"flex", "flex-direction": "column"}} onSubmit={() => {navigate('/login')}}>
            <label>
                New Password: 
                <input type="password" />
            </label>
            <label>
                Confirm New Password: 
                <input type="password" />
            </label>
            <button type="submit">Submit</button>
        </form>
     );
}
 
export default ResetPassword;