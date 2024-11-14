import PropTypes from "prop-types"; // Import prop-types
import { ChevronDown } from "lucide-react";
import "./RoleDropdown.css";

function RoleDropdown({ currentRole, roles, onRoleChange }) {
  return (
    <div className="dropdown-container">
      <select
        value={currentRole}
        onChange={(e) => onRoleChange(e.target.value)}
        className="role-select"
      >
        {roles.map((role) => (
          <option key={role.id} value={role.id}>
            {role.name}
          </option>
        ))}
      </select>
      <div className="dropdown-icon">
        <ChevronDown size={16} />
      </div>
    </div>
  );
}

RoleDropdown.propTypes = {
  currentRole: PropTypes.string.isRequired,
  roles: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
  onRoleChange: PropTypes.func.isRequired,
};

export default RoleDropdown;
