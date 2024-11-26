import { Folder } from "lucide-react"; // Folder icon from lucide-react or another icon library
import { Link } from "react-router-dom"; // For navigation
import PropTypes from "prop-types";
import "./Sidebar.css"; // Style the sidebar

export default function Sidebar({ favorites, important }) {
  return (
    <div className="sidebar">
      <div className="sidebar-group">
        <h3>Favorites</h3>
        <ul className="folder-list">
          {favorites.map((folder) => (
            <li key={folder.id} className="folder-item">
              <Link
                to={`/folders/${folder.folderName}`}
                className="folder-link"
              >
                <Folder className="folder-icon" size={24} />
                {folder.folderName}
              </Link>
            </li>
          ))}
        </ul>
      </div>

      <div className="sidebar-group">
        <h3>Important</h3>
        <ul className="folder-list">
          {important.map((folder) => (
            <li key={folder.id} className="folder-item">
              <Link
                to={`/folders/${folder.folderName}`}
                className="folder-link"
              >
                <Folder className="folder-icon" size={24} />
                {folder.folderName}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

Sidebar.propTypes = {
  favorites: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      folderName: PropTypes.string.isRequired,
    })
  ).isRequired,
  important: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      folderName: PropTypes.string.isRequired,
    })
  ).isRequired,
};
