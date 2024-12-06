
import React, { useState, useEffect } from "react";


const FileOptions = ({ file, handleDelete }) => {
    
    const [fileName, setFileName] = useState(file.name);

  
    const handleFileNameChange = (event) => {
  
      if (event.target.id === "name") {
        setFileName(event.target.value);
    
    };
  }
  

    return (
      <div>
        
        <button onClick={() => handleDelete(file)}>Delete</button>
        
        <div >
          <input
            id="name"
            value={fileName}
            onChange={handleFileNameChange}
          />
        </div>
        
        <div>HARD CODED DATE: 06.12.24</div>
        
      </div>
    );
  };
  
    
export default FileOptions;  