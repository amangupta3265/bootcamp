import axios from 'axios';
import React, { useEffect, useRef, useState } from "react";
import "./note.css";

function SingleNote({ note, id }) {
  const inputRef = useRef('');
  const [userInput, setUserInput] = useState('');
  const [emailInput, setEmailInput] = useState('');
  

  useEffect(() => {
    setUserInput(note.noteContent);
  }, []);

  const editNote = () => {
    setUserInput(inputRef.current.value);
  };

  const saveNote = () => {
    const token = localStorage.getItem('token');
    axios.post(
      `http://localhost:3000/notes/add/`,
      {
        noteContent: userInput
      },
      { headers: { Authorization: `Basic ${token}` }, 'Access-Control-Allow-Origin': 'http://localhost:3000' }
    )
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const deleteNote = (id) => {
    const token = localStorage.getItem('token');
    axios
      .delete(`http://localhost:3000/notes/delete/${id}`, {
        headers: { Authorization: `Basic ${token}`,'Access-Control-Allow-Origin': 'http://localhost:3000' }
      })
      .then(response => {
        console.log(response.data);
        // Perform any necessary actions after successful deletion
      })
      .catch(error => {
        console.log(error);
      });
  };

  const sendNoteToEmail = (id) => {
    const token = localStorage.getItem('token');
    axios
      .post(
        `http://localhost:3000/notes/sendnoteonemail/${id}`,
        {
          email: emailInput,
          id:id
        },
        { headers: { Authorization: `Basic ${token}`,'Access-Control-Allow-Origin': 'http://localhost:3000' } }
      )
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const shareNoteToEmail = () => {
    const token = localStorage.getItem('token');
    axios
      .post(
        `http://localhost:3000/notes/giveaccess/`,
        {
          noteAccess: emailInput
        },
        { headers: { Authorization: `Basic ${token}`,'Access-Control-Allow-Origin': 'http://localhost:3000' } }
      )
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <div className="note">
      <button onClick={editNote}>
        <i className="fa-solid fa-pen-to-square"></i>
      </button>
      <button onClick={()=>deleteNote(id)}>
        <i className="fa-solid fa-trash"></i>
      </button>
      <textarea className="noteInput" value={userInput} onChange={editNote} />
      <button onClick={saveNote}>save</button>
      <input
        type="email"
        value={emailInput}
        onChange={(e) => setEmailInput(e.target.value)}
        placeholder='enter email to share or send'
      />
      <button onClick={(id)=>sendNoteToEmail(id)}>send</button>
      <button onClick={shareNoteToEmail}>share</button>
      
      </div>
  );
}

export default SingleNote;
