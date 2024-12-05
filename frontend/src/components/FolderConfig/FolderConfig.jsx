import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getFolderByNameAsync } from "../../services/folderService";
import "./FolderConfig.css";
import RoleConfig from "./RoleConfig";
import { Button } from "../shared/Button/Button";

export default function FolderConfig() {
  const { folderName } = useParams();
  const [folder, setFolder] = useState(null);
  const [selectedRole, setSelectedRole] = useState(""); // Track selected role
  const [loading, setLoading] = useState(true);

  const [newSubrole, setNewSubrole] = useState(""); // Track new subrole

  const navigate = useNavigate();

  useEffect(() => {
    const loadFolder = async () => {
      try {
        const folderData = await getFolderByNameAsync(folderName);
        setFolder(folderData);
        setSelectedRole(Object.keys(folderData.rolesPermissionsMatrix)[0]); // Default to the first role
      } catch (error) {
        console.error("Error loading folder:", error);
      } finally {
        setLoading(false);
      }
    };

    loadFolder();
  }, [folderName]);

  const handlePermissionChange = (role, subrole, permission) => {
    const updatedPermissions = {
      ...folder.rolesPermissionsMatrix[role],
      [subrole]: {
        ...folder.rolesPermissionsMatrix[role][subrole],
        [permission]: !folder.rolesPermissionsMatrix[role][subrole][permission],
      },
    };

    const updatedMatrix = {
      ...folder.rolesPermissionsMatrix,
      [role]: updatedPermissions,
    };

    setFolder({ ...folder, rolesPermissionsMatrix: updatedMatrix });
  };

  const handleSave = () => {
    // Save the changes
    console.log("Saving...");
  };

  const handleCancel = () => {
    // Navigate back to folders page
    navigate("/folders");
  };

  const handleRoleSelect = (role) => {
    setSelectedRole(role);
  };


  // Add new subrole logic
  const handleAddSubrole = () => {
    if (!newSubrole) return; // If no subrole name, do nothing

    const updatedPermissions = {
      ...folder.rolesPermissionsMatrix[selectedRole],
      [newSubrole]: {
        read: false,
        write: false,
        delete: false,
        download: false,
      },
    };

    const updatedMatrix = {
      ...folder.rolesPermissionsMatrix,
      [selectedRole]: updatedPermissions,
    };

    setFolder({ ...folder, rolesPermissionsMatrix: updatedMatrix });
    setNewSubrole(""); // Clear the input after adding the subrole
  };


  if (loading) {
    return <div>Loading...</div>;
  }

  if (!folder) {
    return <div>Folder not found</div>;
  }

  const selectedRolePermissions = folder.rolesPermissionsMatrix[selectedRole];

  return (
    <div className="folder-config-container">
      <h1>{folderName} Configuration</h1>
      <div className="folder-config">
        <div className="folder-info">

          <h2>{selectedRole} Subrole Matrix</h2>

          <table className="permissions-table">
            <thead>
              <tr>
                <th>Subrole</th>
                {Object.keys(
                  selectedRolePermissions[
                    Object.keys(selectedRolePermissions)[0]
                  ] || {}
                ).map((perm) => (
                  <th key={perm}>
                    {perm.replace(/([A-Z])/g, " $1").toLowerCase()}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {Object.keys(selectedRolePermissions).map((subrole) => (
                <tr key={subrole}>
                  <td>{subrole}</td>
                  {Object.keys(selectedRolePermissions[subrole]).map((perm) => (
                    <td key={perm}>
                      <input
                        type="checkbox"
                        checked={selectedRolePermissions[subrole][perm]}
                        onChange={() =>
                          handlePermissionChange(selectedRole, subrole, perm)
                        }
                      />
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
          <div className="add-subrole-section">
            <input
              type="text"
              value={newSubrole}
              onChange={(e) => setNewSubrole(e.target.value)}
              placeholder="Enter new subrole name"
            />
            <Button text="Add Subrole" action={handleAddSubrole} type={true} />
          </div>

          <h2>{selectedRole}</h2>

          {selectedRole && (
            <table className="permissions-table">
              <thead>
                <tr>
                  <th>Subrole</th>
                  {Object.keys(
                    selectedRolePermissions[
                      Object.keys(selectedRolePermissions)[0]
                    ] || {}
                  ).map((perm) => (
                    <th key={perm}>
                      {perm.replace(/([A-Z])/g, " $1").toLowerCase()}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {Object.keys(selectedRolePermissions).map((subrole) => (
                  <tr key={subrole}>
                    <td>{subrole}</td>
                    {Object.keys(selectedRolePermissions[subrole]).map(
                      (perm) => (
                        <td key={perm}>
                          <input
                            type="checkbox"
                            checked={selectedRolePermissions[subrole][perm]}
                            onChange={() =>
                              handlePermissionChange(
                                selectedRole,
                                subrole,
                                perm
                              )
                            }
                          />
                        </td>
                      )
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          )}

        </div>
        <div className="role-config">
          <RoleConfig
            folder={folder}
            setFolder={setFolder}
            setSelectedRole={setSelectedRole}
          />
        </div>
      </div>

      <div className="folder-config-actions">
        <Button text="Save" action={handleSave} type={true} />
        <Button text="Cancel" action={handleCancel} type={false} />
      </div>
    </div>
  );
}
