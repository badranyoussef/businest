import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Sidebar from "../shared/Sidebar/Sidebar";
import { getFolderByNameAsync } from "../../services/folderService";
import {
  getAllFavoritesByUserIdAsync,
  getAllImportantByUserIdAsync,
} from "../../services/userService";

import "./FolderPage.css";

export default function FolderPage() {
  const { folderName } = useParams(); // Get the folder name from the URL
  const [folder, setFolder] = useState(null);
  const [favorites, setFavorites] = useState([]);
  const [important, setImportant] = useState([]);

  useEffect(() => {
    const loadData = async () => {
      const folder = await getFolderByNameAsync(folderName);
      setFolder(folder);
      const favorites = await getAllFavoritesByUserIdAsync(1);
      setFavorites(favorites);
      const important = await getAllImportantByUserIdAsync(1);
      setImportant(important);
    };
    loadData();
  }, []);

  // Fallback if folder is not found
  if (!folder) {
    return <div>Folder not found</div>;
  }

  return (
    <div className="folder-page">
      <Sidebar favorites={favorites} important={important} />

      <div className="folder-content">
        <h1>Not Yet Implemented</h1>
      </div>
    </div>
  );
}
