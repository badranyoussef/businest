import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AccountManagementPage } from './pages/AccountManagementPage';
import { AccountPage } from './pages/AccountPage';
import FolderConfig from "./components/FolderConfig/FolderConfig";
import { FolderList } from "./components/FolderAdminList/FolderAdminList";
import { Profile } from "./components/Profile/Profile";
import { NotFound } from "./components/NotFound/NotFound";
import { Navbar } from "./components/shared/Navbar/Navbar";
import Footer from "./components/shared/Footer/Footer";
import MyLogs from './page/MyLogs';
import FileList from "./page/FileList";
import PathFinder from "./page/FileExplorer.jsx";


function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="app-body">
        <Routes>
          <Route path="/" element={<Navigate to="/account" replace />} />
          <Route path="/account" element={<AccountManagementPage />} />
          <Route path="/account/:id" element={<AccountPage />} />
          <Route path="/folders" element={<FolderList />} />
          <Route
            path="/folders/:folderName/configure"
            element={<FolderConfig />}
          />
          <Route path="/profile" element={<Profile />} />
          <Route path="/logs" element={<MyLogs />} />
          <Route path="/folderTest" element={<FileList />} />

          <Route path="*" element={<NotFound />} />
          <Route path="/pathfinder" element={<PathFinder />} />
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}
export default App;