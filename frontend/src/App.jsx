import { BrowserRouter, Routes, Route } from "react-router-dom";
import FolderConfig from "./components/FolderConfig/FolderConfig";
import { FolderList } from "./components/FolderAdminList/FolderAdminList";
import { Profile } from "./components/Profile/Profile";
import { NotFound } from "./components/NotFound/NotFound";
import { Navbar } from "./components/shared/Navbar/Navbar";
import Footer from "./components/shared/Footer/Footer";

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="app-body">
        <Routes>
          <Route path="/" element={<FolderList />} />
          <Route
            path="/folders/:folderId/configure"
            element={<FolderConfig />}
          />
          <Route path="/profile" element={<Profile />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}
