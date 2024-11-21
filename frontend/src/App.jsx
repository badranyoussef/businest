import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Profile } from "./components/Profile/Profile";
import "./App.css";
import { FolderList } from "./components/FolderList/FolderList";
import { NotFound } from "./components/NotFound/notFound";
import { Navbar } from "./components/NavBar/Navbar";

export function App() {
  return (
    <Router>
      <div className="app">
        <Navbar />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<FolderList />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}
