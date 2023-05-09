import axios from 'axios';
import React, { useEffect, useRef, useState } from "react";
import "./note.css";

function SingleNote({note,id}) {
  const [noteData,setNoteData]=useState({})
  const inputRef=useRef('')
  const [userInput,setUserInput]=useState('') 

  useEffect(()=>{
    setUserInput(note)
  },[])

  return <div className="note" >
    <textarea className="noteInput" value={userInput} />
    </div>;
}

export default SingleNote;
