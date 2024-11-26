import { useState, useMemo, useEffect } from "react";
import { Pagination } from "../shared/Pagination/Pagination";
import { SearchBar } from "../shared/SearchBar/SearchBar";
import { Folder } from "lucide-react";
import { Link } from "react-router-dom";
import "./AllFoldersPage.css"; // You will need to create this CSS for grid styling
import { getAllFoldersByCompanyNameAsync } from "../../services/folderService";

export function AllFoldersPage() {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState("");
  const itemsPerPage = 8; // Show more items in the grid view

  const [companyName] = useState("example");
  const [folders, setFolders] = useState([]);
  const [errorMessage, setErrorMessage] = useState(null);

  useEffect(() => {
    const fetchFolders = async () => {
      try {
        const fetchedFolders = await getAllFoldersByCompanyNameAsync(companyName);
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
    fetchFolders();
  }, []);

  useEffect(() => {
    setCurrentPage(1);
  }, [searchQuery]);

  const filteredFolders = useMemo(() => {
    if (!searchQuery) return folders;

    const query = searchQuery.toLowerCase();
    return folders.filter((folder) => {
      // Check folder name
      const folderNameMatch = folder.folderName.toLowerCase().includes(query);

      // Check if any role or subrole matches
      const roleMatch = Object.keys(folder.rolesPermissionsMatrix).some(
        (role) => {
          return folder.rolesPermissionsMatrix[role].some((subrole) =>
            subrole.toLowerCase().includes(query)
          );
        }
      );

      return folderNameMatch || roleMatch;
    });
  }, [folders, searchQuery]);

  const totalPages = Math.ceil(filteredFolders.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = Math.min(currentPage * itemsPerPage, filteredFolders.length);
  const currentFolders = filteredFolders.slice(startIndex, endIndex);

  return (
    <div className="all-folders-page">
      <div className="all-folders-header">
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

      <div className="folders-grid">
        {currentFolders.map((folder) => (
          <div className="folder-item" key={folder.id}>
            <Link to={`/folder/${folder.folderName}`} className="folder-link">
              <Folder className="folder-icon" size={48} />
              <div className="folder-name">{folder.folderName}</div>
            </Link>
          </div>
        ))}

        {currentFolders.length === 0 && (
          <div className="empty-message">
            No folders found matching your search.
          </div>
        )}
      </div>

      <div className="pagination-footer">
        <div className="display-message">
          Displaying {startIndex + 1} - {endIndex} / {filteredFolders.length}
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
