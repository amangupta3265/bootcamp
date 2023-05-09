import React, { useState, useRef } from "react";
import "./Editor.css";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { saveAs } from "file-saver";
const Editor = ({ saveNote }) => {
  const editorRef = useRef(null);
  const [text, setText] = useState("");

  const exportToFile = () => {
    if (editorRef.current && editorRef.current.editor) {
      const editorData = editorRef.current.editor.getData();
      const blob = new Blob([editorData], { type: "text/plain;charset=utf-8" });
      saveAs(blob, "myFile.txt");
    }
  };
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        const fileContents = reader.result;
        setText((prevText) => prevText + fileContents);
      };
      reader.readAsText(file);
    }
  };

  return (
    <div className="editor-box">
      {" "}
      <div className="content-div">
        {" "}
        <CKEditor
          editor={ClassicEditor}
          data={text}
          onChange={(event, editor) => {
            const data = editor.getData();
            setText(data);
            document.getElementById("notePreview").innerHTML = text;
            //console.log(document.getElementById("notePreview"), "hello");
          }}
          ref={editorRef}
        />{" "}
        <button onClick={() => saveNote(text)}>submit</button>
        <button onClick={exportToFile}>Export</button>{" "}
        <input type="file" accept=".txt" onChange={handleFileChange} />{" "}
        <div>
          <h2>Content</h2> <p>{text}</p>
          <h2>Preview</h2>
          <div id="notePreview"></div>
        </div>{" "}
      </div>{" "}
    </div>
  );
};
export default Editor;
