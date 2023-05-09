import React, { useEffect, useState } from "react";
import axios from "axios";
import SingleNote from "./SingleNote";
import Editor from "../editor/Editor";
import './note.css';
import Navbar from "../../components/navbar/Navbar";

function MyNotes() {
  const [notes, setNotes] = useState([{noteId:1,noteContent:"Hello"}]);

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

  const saveNote = (text) => {
    const token= localStorage.getItem('token')
    axios.post(`http://localhost:3000/notes/add`, {
      noteContent:text
    }, {
        headers: {
          Authorization: `Basic ${token}`,
          'Access-Control-Allow-Origin': 'http://localhost:3000'
        }
      })
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }

  let noteList = notes.map(note => <SingleNote key={note.id} id={note.noteId} note={note}/>);

  return (
    <>
    <Navbar/>
    <div className="notesContainer">
      <Editor className="noteEditor" saveNote={saveNote} />
      <h2>Notes List</h2>
      <div className="noteList">
        {noteList}
      </div>
    </div>
    </>
    
  );
}

export default MyNotes;
