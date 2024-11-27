import React from 'react';

export function RoleSelector({ selectedRole, roles, onChange, disabled }) {
  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">
        Role
      </label>
      <select
        value={selectedRole}
        onChange={(e) => onChange(e.target.value)}
        disabled={disabled}
        className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 disabled:bg-gray-100 disabled:cursor-not-allowed"
      >
        <option value="">Select a role</option>
        {roles.map((role) => (
          <option key={role} value={role}>
            {role}
          </option>
        ))}
      </select>
    </div>
  );
}