import React from 'react';
import { ChevronDown } from 'lucide-react';
import '../../styles/RoleDropdown.css';

export default function RoleDropdown({ currentRole, roles, onRoleChange }) {
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
