import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AccountManagementPage } from './pages/AccountManagementPage';
import { AccountPage } from './pages/AccountPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/account" replace />} />
        <Route path="/account" element={<AccountManagementPage />} />
        <Route path="/account/:id" element={<AccountPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;