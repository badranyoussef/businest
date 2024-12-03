import React, { useState, useEffect } from "react";
import { getFiles } from "C:/Inteli/businest/frontend/src/services/folderService.js";


const FileOptions = ({ file, handleDelete }) => {
    
  const [fileName, setFileName] = useState(file.name);
  const [fileId, setFileId] = useState(file.id);
  const [fileType, setFileType] = useState(file.type);


  const handleFileNameChange = (event) => {

    if (event.target.id === "name") {
      setFileName(event.target.value);
  
  };
}

  const handleOpenFile = () => {

    // navigate("/PageWithFileOpened/${fileId}");
    
  };

  return (
    <div className="file-wrapper">
      <i className="bx bx-x" onClick={() => handleDelete(file)}></i>
      <div className="file-details">
        <input
          id="name"
          value={fileName}
          onChange={handleFileNameChange}
        />
      
      </div>
      <div className="file-date">{file.date}</div>
      
      <button
        onClick={() => handleOpenFile()}
      >
        Save
      </button>
    </div>
  );
};

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