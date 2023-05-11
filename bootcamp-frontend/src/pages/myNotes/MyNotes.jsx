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
    console.log(token)
    axios.get('http://localhost:8080/notes/getmynotes', {
      headers: {
        Authorization: `Bearer ${token}`,
        'Access-Control-Allow-Origin': '*'
      }
    })
    .then(res => {
      setNotes(res.data);
    })
    .catch(error => {
      console.log("something went wrong in fetching error");
    })
  },[])

  const saveNote = (event,text) => {
    event.preventDefault();
    const token= localStorage.getItem('token')
    console.log(token)
    axios.post(`http://localhost:8080/notes/add`, {
      noteId:notes.length,
      noteTitle:text.substr(0,5),
      noteContent:text,
      withCredentials: true
    }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
          'Access-Control-Allow-Origin': '*'
        }
      })
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }

  let noteList = notes.map(note => <SingleNote key={note.noteId} id={note.noteId} note={note}/>);

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
