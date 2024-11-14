import { useState, useMemo, useEffect } from "react";
import { Pagination } from "../shared/Pagination";
import { SearchBar } from "../shared/SearchBar";
import { Folder } from "lucide-react";
import "./FolderList.css";
import {
  getAllFoldersAsync,
  mockFolders,
  updateFolderRoleAsync,
} from "../../services/folderService";

export function FolderList() {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState("");
  const itemsPerPage = 5;

  // Initially set folders from mock data
  const [folders, setFolders] = useState(mockFolders || []);
  const [errorMessage, setErrorMessage] = useState(null);
  const [companyName] = useState("YourCompanyName");

  const roles = ["Admin", "Manager", "User", "Guest"];

  useEffect(() => {
    fetchFolders();
  }, [companyName]);

  const handleRoleChange = async (folderId, newRole) => {
    try {
      setErrorMessage(null);
      await updateFolderRoleAsync(folderId, newRole);
      setFolders((folders) =>
        folders.map((folder) =>
          folder.id === folderId ? { ...folder, role: newRole } : folder
        )
      );
      console.log(`Successfully updated folder ${folderId} to role ${newRole}`);
    } catch (error) {
      setErrorMessage(`Failed to update folder ${folderId}: ${error.message}`);
      console.error(
        `Failed to update folder ${folderId} role: ${error.message}`
      );
    }
  };

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
    return Array.isArray(folders)
      ? folders.filter(
          (folder) =>
            folder.folderName.toLowerCase().includes(query) ||
            folder.role.toLowerCase().includes(query)
        )
      : [];
  }, [folders, searchQuery]);

  // Reset to first page when search query changes
  useEffect(() => {
    setCurrentPage(1);
  }, [searchQuery]);

  // Pagination calculations
  const totalPages = Math.ceil(filteredFolders.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentFolders = filteredFolders.slice(startIndex, endIndex);

  return (
    <div className="role-list">
      <div className="role-list-header">
        <h1>Folder Permissions</h1>
        <SearchBar
          value={searchQuery}
          onChange={setSearchQuery}
          placeholder="Search folders..."
        />
      </div>

      {/* Display error message if an error occurs */}
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
              <th>Permission</th>
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
                  <select
                    value={folder.role}
                    onChange={(e) =>
                      handleRoleChange(folder.id, e.target.value)
                    }
                    className={`role-select ${folder.role
                      .toLowerCase()
                      .replace(" ", "-")}`}
                  >
                    {roles.map((role) => (
                      <option key={role} value={role}>
                        {role}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
            {currentFolders.length === 0 && (
              <tr>
                <td colSpan={2} className="empty-message">
                  No folders found matching your search.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="table-footer">
        <div className="entry-count">
          {filteredFolders.length > 0
            ? `Showing ${startIndex + 1}-${Math.min(
                endIndex,
                filteredFolders.length
              )} of ${filteredFolders.length} folders`
            : "No folders to show"}
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
