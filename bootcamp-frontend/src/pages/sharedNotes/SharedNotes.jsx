import React, { useEffect, useState } from "react";
import axios from "axios";
import SingleNote from "./SingleNote";
import Editor from "../editor/Editor";
import './note.css';
import Navbar from "../../components/navbar/Navbar";

function SharedNotes() {
  const [notes, setNotes] = useState([]);

  useEffect(()=>{
    const token = localStorage.getItem('token');
    axios.get('http://localhost:3000/notes/getmynotes', {
      headers: {
        Authorization: `Basic ${token}`,
        'Access-Control-Allow-Origin': 'http://localhost:3000'
      }
    })
    .then(res => {
      setNotes(res.data);
    })
    .catch(error => {
      console.log("something went wrong in fetching error");
    })
  },[])

  let noteList = notes.map(note => <SingleNote key={note.id} id={note.noteId} note={note}/>);

  return (
    <>
    <Navbar/>
    <div className="notesContainer">
      <h2>Notes List</h2>
      <div className="noteList">
        {noteList}
      </div>
    </div>
    </>
    
  );
}

export default SharedNotes;
