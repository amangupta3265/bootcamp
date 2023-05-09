import "./profile.scss";
import Navbar from "../../components/navbar/Navbar";
import { useRef } from "react";
import { useEffect,useState } from "react";
import axios from "axios"

const Profile = () => {
  const nameRef = useRef(null)
  const emailRef = useRef(null)
  const imageRef = useRef(null)
  const [user, setUser] = useState({name:"",email:""});
  const [userImg,setUserImg] = useState({})
  useEffect(() => {
    const getUser = async () => {
      const res = await axios({
        method: "get",
        url: "localhost:8080/user",
        withCredentials: true
      })
      console.log(res)
      setUser(res.user)
    }

    getUser()
  }, [])

  


  const saveDetails = async (e) => {
    try {
      e.preventDefault();
      const res = await axios({
        method: "post",
        url: "localhost:3000/user",
        data: {
          name: nameRef.current.value,
          email: emailRef.current.value,
        },
        withCredentials: true,
      });

      if (res.status === "success") {
        alert("Saved details successfully");
        setUser(res.user);
      }
    } catch (err) {
      alert(err);
    }
  };

  const handleFileChange = (e) => {
    e.preventDefault()
    console.log(e.target.files[0])
    setUserImg({
      img_64: URL.createObjectURL(e.target.files[0]),
      img_f: e.target.files[0]
    })
  }

  const handleSubmitFile = async(e) =>{
    try{
      e.preventDefault()
      let formData = new FormData();
      formData.append('image', userImg.img_f);
      // the image field name should be similar to your api endpoint field name
      // in my case here the field name is customFile
      const res = await axios({
        method:"post",
        url:"localhost:8080",
        data:formData,
        headers:{
          'Content-Type': 'multipart/form-data'
        },
      })
      console.log(res)
    }
    catch(err){
      console.log(err)
    }
  }

  

  return (
    <div className="profile">
      <Navbar />
      <div className="wrapper">

        <div className="content">
          <form className="ui form">
            <h2 class="ui dividing header">My Profile</h2>
            <div className="field">
              <label>Name</label>
              <div className="field">
                <input
                  ref={nameRef}
                  type="text"
                  name="first-name"
                  placeholder={user.name}
                />
              </div>
            </div>
            <div className="field">
              <label>Email</label>
              <input
                ref={emailRef}
                type="email"
                name="email"
                placeholder={user.email}
              />
            </div>
            <button class="ui red button" type="submit" onClick={saveDetails}>
              Save Details
            </button>
            <div className="right">
                <img
                    // src="https://images.pexels.com/photos/6899260/pexels-photo-6899260.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                    src="//unsplash.it/100/100"
                    // src={require("../../../public/logo192.png")}
                    alt=""
                />
            </div>
            <input 
              name="image" 
              type="file"
              onChange={handleFileChange}
              >
            </input>
            <button class="ui red button" onClick={handleSubmitFile}>
              Update Profile Picture
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Profile;