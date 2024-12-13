import React from 'react';
import { SubroleSelector } from './SubroleSelector';

export function RoleSelector({ selectedRoles, roles, onChange, disabled }) {
  const handleRoleChange = (role) => {
    const updatedRoles = selectedRoles.some(selected => selected.title === role.title)
      ? selectedRoles.filter(selected => selected.title !== role.title)
      : [...selectedRoles, { ...role, subRoles: role.subRoles ? [] : undefined }];
    onChange(updatedRoles);
  };

  const handleSubroleChange = (role, updatedSubroles) => {
    const updatedRoles = selectedRoles.map(selected =>
      selected.title === role.title
        ? { ...selected, subRoles: updatedSubroles }
        : selected
    );
    onChange(updatedRoles);
  };

  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">
        Role
      </label>
      <div className="space-y-2 max-h-48 overflow-y-auto p-2 border border-gray-200 rounded-md">
        {roles.map((role) => {
          const selectedRole = selectedRoles.find(selected => selected.title === role.title) || {};
          return (
            <label key={role.title} className="flex flex-col space-y-1 p-1 hover:bg-gray-50 rounded">
              <div className="flex items-center space-x-2">
                <input
                  type="checkbox"
                  checked={selectedRoles.some(selected => selected.title === role.title)}
                  onChange={() => handleRoleChange(role)}
                  disabled={disabled}
                  className="rounded border-gray-300 text-blue-600 focus:ring-blue-500 disabled:opacity-50"
                />
                <span className="text-sm text-gray-700">{role.title}</span>
              </div>
              {role.subRoles && (
                <SubroleSelector
                  selectedSubroles={selectedRole.subRoles || []}
                  availableSubroles={role.subRoles}
                  onChange={(updatedSubroles) => handleSubroleChange(role, updatedSubroles)}
                  disabled={disabled}
                />
              )}
            </label>
          );
        })}
      </div>
    </div>
  );
}
