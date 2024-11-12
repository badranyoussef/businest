import React, { useEffect, useState } from "react";

import {
  readAllLogs,

} from "../services/logService";

import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import AddLogs from "../page/AddLogs";

Modal.setAppElement("#root");

const Log = ({ log, handleDelete, handleUpdateLog }) => {

  const [logupdatedUser, setLogupdatedUser] = useState(log.updatateduser);
  const [logTitle, setLogTitle] = useState(log.title);
  const [logDescription, setDescription] = useState(log.Description);
  const [logDate, setLogDate] = useState(log.date);
  const [logAccountEditor, setContentChanged] = useState(false);

 
  };

  return (
    <>
      <LogWrapper>
        
        <StyledDate>{log.date}</StyledDate>
        <ContentWrapper>
          
            <StyledTitleDisplay>
              {logTitle}
            </StyledTitleDisplay>
      

          <StyledTextArea
            value={logContent}
            onChange={handleInputChange}
          />
          </ContentWrapper>

      </LogWrapper>
    </>
  );


function MyLogs() {
  const navigate = useNavigate();


  const [allLogss, setAllLogs] = useState([]);
  const [logs, setLogs] = useState([]);


  const fetchAllLogs = async () => {
    const allLogs = await readAllLogs();
    console.log(allLogs);
    setAllLogs(allLogs);
    setLogs(allLogs);
  };

  useEffect(() => {
    fetchAllLogs();
  }, []);

  useEffect(() => {
    filterLogs(query);
  }, [query]);

  return (
    <PageContainer>
      
      <MyLogsBody $oneLog={logs.length === 1}>
        {logs.map((log) => (
          <LogContainer key={log.id}>
            <Log
              log={log}
              handleDelete={handleDelete}
              handleUpdateLog={handleUpdateLog}
            />
          </LogContainer>
        ))}
      </MyLogsBody>
    </PageContainer>
  );
}

export default MyLogs;

