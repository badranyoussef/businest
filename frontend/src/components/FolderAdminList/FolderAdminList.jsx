import { useState, useMemo, useEffect } from "react";
import { Pagination } from "../shared/Pagination/Pagination";
import { SearchBar } from "../shared/SearchBar/SearchBar";
import { Folder, Settings } from "lucide-react";
import { Link } from "react-router-dom";
import "./FolderAdminList.css";
import {
  getAllFoldersAsync,
  // getAllRolesAsync,
  mockFolders,
  // mockRoles,
  // updateFolderRoleAsync,
} from "../../services/folderService";

export function FolderList() {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState("");
  const itemsPerPage = 4;

  const [companyName] = useState("example");
  const [folders, setFolders] = useState(mockFolders);
  const [errorMessage, setErrorMessage] = useState(null);

  useEffect(() => {
    fetchFolders();
  }, []);

  useEffect(() => {
    setCurrentPage(1);
  }, [searchQuery]);

  const fetchFolders = async () => {
    try {
      const fetchedFolders = await getAllFoldersAsync(companyName);
      if (Array.isArray(fetchedFolders)) {
        setFolders(fetchedFolders);
      } else {
        console.error("Fetched data is not an array:", fetchedFolders);
      }
    } catch (error) {
      console.error("Error fetching folders:", error);
      setErrorMessage("Failed to fetch folders");
    }
  };

  const filteredFolders = useMemo(() => {
    if (!searchQuery) return folders;

    const query = searchQuery.toLowerCase();
    return folders.filter(
      (folder) =>
        folder.folderName.toLowerCase().includes(query) ||
        folder.roles.some((role) => role.toLowerCase().includes(query))
    );
  }, [folders, searchQuery]);

  const totalPages = Math.ceil(filteredFolders.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage + 1;
  const endIndex = Math.min(currentPage * itemsPerPage, filteredFolders.length);
  const currentFolders = filteredFolders.slice(startIndex - 1, endIndex);

  return (
    <div className="role-list">
      <div className="role-list-header">
        <h1>All Folders</h1>
        <SearchBar
          value={searchQuery}
          onChange={setSearchQuery}
          placeholder="Search folders..."
        />
      </div>

      {errorMessage && (
        <div className="error-message">
          <strong>Error:</strong> {errorMessage}
        </div>
      )}

      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Folder Name</th>
              <th>Files</th>
              <th>Permissions</th>
              <th>Last Updated</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {currentFolders.map((folder) => (
              <tr key={folder.id}>
                <td className="folder-name">
                  <Folder className="folder-icon" size={18} />
                  {" " + folder.folderName}
                </td>
                <td>
                  {folder.numberOfFiles} file{folder.numberOfFiles !== 1 && "s"}
                </td>
                <td>
                  {folder.roles.map((role, index) => (
                    <span key={role}>
                      {role}
                      {index < folder.roles.length - 1 && ", "}
                    </span>
                  ))}
                </td>
                <td>
                  {folder.lastUpdated.toLocaleDateString("en-US", {
                    year: "numeric",
                    month: "short",
                    day: "numeric",
                  })}
                </td>
                <td>
                  <Link to={`/folders/${folder.folderName}/configure`}>
                    <Settings className="cog-icon" size={18} />
                  </Link>{" "}
                </td>
              </tr>
            ))}
            {currentFolders.length === 0 && (
              <tr>
                <td colSpan={5} className="empty-message">
                  No folders found matching your search.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="table-footer">
        <div className="display-message">
          Displaying {startIndex} - {endIndex} / {filteredFolders.length}
        </div>
        {filteredFolders.length > 0 && (
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={setCurrentPage}
          />
        )}
      </div>
    </div>
  );
}
