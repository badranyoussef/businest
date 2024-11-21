import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getFolderByNameAsync } from "../../services/folderService";
import "./FolderConfig.css";

export default function FolderConfig() {
  const { folderName } = useParams();
  const [folder, setFolder] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("folderName", folderName);
    const loadFolder = async () => {
      try {
        const folderData = await getFolderByNameAsync(folderName);
        setFolder(folderData);
      } catch (error) {
        console.error("Error loading folder:", error);
      } finally {
        setLoading(false);
      }
    };

    loadFolder();
  }, [folderName]);

  const handlePermissionChange = (role, permission) => {
    const updatedPermissions = folder.rolesPermissionsMatrix.map((rolePerm) => {
      if (rolePerm.role === role) {
        return {
          ...rolePerm,
          permissions: {
            ...rolePerm.permissions,
            [permission]: !rolePerm.permissions[permission],
          },
        };
      }
      return rolePerm;
    });

    setFolder({ ...folder, rolesPermissionsMatrix: updatedPermissions });
  };

  const handleSave = () => {
    // Save the changes
    console.log("Saving...");
  };

  const handleCancel = () => {
    // Navigate back to folders page
    navigate("/folders");
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!folder) {
    return <div>Folder not found</div>;
  }

  return (
    <div className="folder-config">
      <h1>Folder Configuration</h1>
      <div className="folder-info">
        <h2>{folder.folderName}</h2>
        <table className="permissions-table">
          <thead>
            <tr>
              <th>Role</th>
              {Object.keys(folder.rolesPermissionsMatrix[0].permissions).map(
                (perm) => (
                  <th key={perm}>
                    {perm.replace(/([A-Z])/g, " $1").toLowerCase()}
                  </th>
                )
              )}
            </tr>
          </thead>
          <tbody>
            {folder.rolesPermissionsMatrix.map((rolePerm) => (
              <tr key={rolePerm.role}>
                <td>{rolePerm.role}</td>
                {Object.keys(rolePerm.permissions).map((perm) => (
                  <td key={perm}>
                    <input
                      type="checkbox"
                      checked={rolePerm.permissions[perm]}
                      onChange={() =>
                        handlePermissionChange(rolePerm.role, perm)
                      }
                    />
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
        <div className="folder-config-actions">
          <button onClick={handleSave}>Save</button> {/* Corrected */}
          <button onClick={handleCancel}>Cancel</button> {/* Corrected */}
        </div>
      </div>
    </div>
  );
}
