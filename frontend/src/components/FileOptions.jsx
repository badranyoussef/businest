import React, { useState } from "react";

const FileOptions = ({ file, handleDelete }) => {
  const [fileName, setFileName] = useState(file.name);

  const handleFileNameChange = (event) => {
    if (event.target.id === "name") {
      setFileName(event.target.value);
    }
  };

  const handleDeleteClick = () => {
    if (window.confirm("Are you sure you want to delete this file?")) {
      handleDelete(file);
    }
  };

  const fileWrapperStyle = {
    position: 'relative',
    border: '1px solid #ccc',
    padding: '10px',
    paddingTop: '30px', 
    borderRadius: '5px',
    width: '200px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    marginBottom: '20px',
  };

  const inputStyle = {
    width: '100%',
    padding: '5px',
    marginBottom: '10px',
  };

  const deleteButtonStyle = {
    position: 'absolute',
    top: '5px',
    right: '5px',
    backgroundColor: '#f44336',
    color: 'white',
    border: 'none',
    padding: '5px',
    borderRadius: '50%',
    cursor: 'pointer',
    width: '20px',
    height: '20px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  };

  return (
    <div style={fileWrapperStyle}>
      <button style={deleteButtonStyle} onClick={handleDeleteClick}>×</button>
      <div>
        <input
          id="name"
          value={fileName}
          onChange={handleFileNameChange}
          style={inputStyle}
        />
        <p>Type: {file.type}</p>
      </div>
      <div>HARD CODED DATE: 06.12.24</div>
    </div>
  );
};

export default FileOptions;