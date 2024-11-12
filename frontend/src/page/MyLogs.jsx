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
      {logs.length > 0 ? (
        logs.map((log) => (
          <div key={log.id}>
            <h2>{log.title}</h2>
            <p>{log.description}</p>
            <p>{log.date}</p>
          </div>
        ))
      ) : (
        <p>No logs available</p>
      )}
    </div>
  );
};

export default MyLogs;
