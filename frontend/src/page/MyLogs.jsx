import React, { useEffect, useState } from "react";
import { readAllLogs } from "C:/Inteli/businest/frontend/src/services/logServices.js";

const MyLogs = () => {
  const [logs, setLogs] = useState([]);

  useEffect(() => {
    readAllLogs().then((data) => setLogs(data));
  }, []);

  

  return (
    <div>
      <h1>My Logs</h1>
      <div style={gridStyle}>
        <div style={headerStyle}>
          <div style={cellStyle}>Title</div>
          <div style={cellStyle}>Description</div>
          <div style={cellStyle}>Date</div>
        </div>
        {logs.length > 0 ? (
          logs.map((log) => (
            <div style={rowStyle} key={log.id}>
              <div style={cellStyle}>{log.title}</div>
              <div style={cellStyle}>{log.description}</div>
              <div style={cellStyle}>{log.date}</div>
            </div>
          ))
        ) : (
          <p>No logs available</p>
        )}
      </div>
    </div>
  );
};

const gridStyle = {
  display: 'grid',
  gridTemplateColumns: 'repeat(3, 1fr)',
  gap: '10px',
};

const headerStyle = {
  display: 'contents',
  fontWeight: 'bold',
};

const rowStyle = {
  display: 'contents',
};

const cellStyle = {
  padding: '10px',
  border: '1px solid #ccc',
};

export default MyLogs;
