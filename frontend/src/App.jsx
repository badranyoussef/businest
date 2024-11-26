import { BrowserRouter, Routes, Route } from "react-router-dom";
import FolderConfig from "./components/FolderConfig/FolderConfig";
import { FolderAdminList } from "./components/FolderAdminList/FolderAdminList";
import { Profile } from "./components/Profile/Profile";
import { NotFound } from "./components/NotFound/NotFound";
import { Navbar } from "./components/shared/Navbar/Navbar";
import Footer from "./components/shared/Footer/Footer";
import { AllFoldersPage } from "./components/AllFoldersPage/AllFoldersPage";
import FolderPage from "./components/FolderPage/FolderPage";

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="app-body">
        <Routes>
          <Route path="/folders" element={<AllFoldersPage />} />
          <Route path="/folder/:folderName" element={<FolderPage />} />
          <Route path="/admin/folders" element={<FolderAdminList />} />
          <Route
            path="/folders/:folderName/configure"
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
