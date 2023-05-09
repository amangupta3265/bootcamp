import axios from 'axios';
import React, { useEffect, useRef, useState } from "react";
import "./note.css";

function SingleNote({note,id}) {
  const [noteData,setNoteData]=useState({})
  const inputRef=useRef('')
  const [userInput,setUserInput]=useState('') 
  const [vote,setVote]=useState(0)

  useEffect(()=>{
    setUserInput(note)
  },[])


  const editNote=(id)=>{
    setUserInput(inputRef.current.value);
  }

  const upVote=(id)=>{
   
    axios.put(`http://localhost:3000/notes/upvote/${id}`,{
      headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': 'http://localhost:3000'
        }
  }).then(response => {
        console.log(response.data);
        setVote(vote+1)
      })
      .catch(error => {
        console.log(error);
      });
  }

  const downVote=(id)=>{

    axios.put(`http://localhost:3000/notes/downvote/${id}`,{
      headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': 'http://localhost:3000'
        }
  }).then(response => {
        console.log(response.data);
        setVote(vote-1)
      })
      .catch(error => {
        console.log(error);
      });
  }

  return <div className="note" >
    
    <textarea className="noteInput" value={userInput} onChange={editNote} />
    <div>
    <button onClick={()=>upVote(id)}>upVote</button>
    <span>vote</span>
    <button onClick={()=>downVote(id)}>downVote</button>
    </div>

    </div>;
}

export default SingleNote;
