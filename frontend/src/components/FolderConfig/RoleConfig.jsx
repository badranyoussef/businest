import { useState } from "react";
import PropTypes from "prop-types";
import { mockRoles } from "../../services/folderService";
import "./RoleConfig.css";
import { Button } from "../shared/Button/Button";

function RoleConfig({ folder, setFolder, setSelectedRole }) {
  const [newRole, setNewRole] = useState(""); // Role to be added
  const availableRoles = mockRoles; // The available roles to choose from

  // Ensure that rolesPermissionsMatrix is an object (it should be already)
  const rolesPermissionsMatrix = folder.rolesPermissionsMatrix || {};

  // Add a new role with default permissions (all subroles default to permissions false)
  const handleAddRole = () => {
    if (!newRole) return;

    // Check if the role already exists
    if (rolesPermissionsMatrix[newRole]) {
      alert("This role already exists.");
      return;
    }

    // Default permissions for new role
    const newRolePermissions = {
      [newRole]: {
        lead: Object.keys(
          rolesPermissionsMatrix[Object.keys(rolesPermissionsMatrix)[0]] || {}
        ).reduce((acc, permission) => ({ ...acc, [permission]: false }), {}),
        guest: Object.keys(
          rolesPermissionsMatrix[Object.keys(rolesPermissionsMatrix)[0]] || {}
        ).reduce((acc, permission) => ({ ...acc, [permission]: false }), {}),
      },
    };

    // Add the new role to the matrix
    const updatedMatrix = { ...rolesPermissionsMatrix, ...newRolePermissions };
    setFolder({ ...folder, rolesPermissionsMatrix: updatedMatrix });
    setNewRole(""); // Clear the input field
  };

  // Remove a role from the folder
  const handleRemoveRole = (roleToRemove) => {
    const { [roleToRemove]: _, ...updatedMatrix } = rolesPermissionsMatrix;
    setFolder({ ...folder, rolesPermissionsMatrix: updatedMatrix });
  };

  return (
    <div className="role-manager">
      <h3>Manage Roles</h3>

      {/* Dropdown to add new roles */}
      <div className="add-role-section">
        <select
          value={newRole}
          onChange={(e) => setNewRole(e.target.value)}
          className="add-role-dropdown"
        >
          <option value="">Select Role</option>
          {availableRoles
            .filter(
              (role) =>
                !Object.prototype.hasOwnProperty.call(
                  rolesPermissionsMatrix,
                  role
                ) // Ensure role is not already added
            )
            .map((role) => (
              <option key={role} value={role}>
                {role}
              </option>
            ))}
        </select>
        <Button text="Add Role" action={handleAddRole} type={true} />
      </div>
      <ul className="roles-list">
        {Object.keys(rolesPermissionsMatrix).map((role) => (
          <li key={role}>
            {role}
            <Button
              text="select"
              action={() => setSelectedRole(role)}
              type={true}
            />
            <Button
              text="Remove"
              action={() => handleRemoveRole(role)}
              type={false}
            />
          </li>
        ))}
      </ul>
    </div>
  );
}

RoleConfig.propTypes = {
  folder: PropTypes.shape({
    rolesPermissionsMatrix: PropTypes.object.isRequired, // Ensure it's an object now
  }).isRequired,
  setFolder: PropTypes.func.isRequired,
  setSelectedRole: PropTypes.func.isRequired,
};

export default RoleConfig;
