import React, { useState, useEffect } from "react";
import FileOptions from "../components/FileOptions.jsx";
//import { getFiles } from "../services/folderService.js";
import { useNavigate } from "react-router-dom";



const FileList = () => {
    
  const navigate = useNavigate();

  const [files, setFiles] = useState([]);


  useEffect(() => {

    //getFiles().then((data) => setFiles(data));
    const file = {"id": "2", "name": "title2", "type": "pdf"};

    setFiles([file]);

  }, []);


  const handleDelete = (fileToDelete) => {
    setFiles(files.filter((file) => file.id !== fileToDelete.id));
  };

  return (
    <div>
      {files.map((file) => (
        <FileOptions
          key={file.id}
          file={file}
          handleDelete={handleDelete}
        />
      ))}
    </div>
  );
};

export default FileList;