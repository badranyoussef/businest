import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getFolderByIdAsync, updateFolderPermissionsAsync } from '../../services/folderService';
import './FolderConfig.css'; // Make sure you create this file

function FolderConfig() {
  const { folderId } = useParams();
  const [folder, setFolder] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadFolder = async () => {
      try {
        const folderData = await getFolderByIdAsync(folderId);
        setFolder(folderData);
      } catch (error) {
        console.error('Error loading folder:', error);
      } finally {
        setLoading(false);
      }
    };

    loadFolder();
  }, [folderId]);

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
        <h2>Folder: {folder.folderName}</h2>
        <table className="permissions-table">
          <thead>
            <tr>
              <th>Permission</th>
              {Object.keys(folder.permissions).map((role) => (
                <th key={role}>{role}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {Object.keys(folder.permissions[Object.keys(folder.permissions)[0]]).map((perm) => (
              <tr key={perm}>
                <td>{perm.replace(/([A-Z])/g, ' $1').toLowerCase()}</td>
                {Object.entries(folder.permissions).map(([role, perms]) => (
                  <td key={role}>
                    <input
                      type="checkbox"
                      checked={perms[perm]}
                      onChange={async () => {
                        const newPerms = {
                          ...folder.permissions,
                          [role]: {
                            ...folder.permissions[role],
                            [perm]: !perms[perm],
                          },
                        };
                        await updateFolderPermissionsAsync(folderId, newPerms);
                        setFolder({
                          ...folder,
                          permissions: newPerms,
                        });
                      }}
                    />
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default FolderConfig;
