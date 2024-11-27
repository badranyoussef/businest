import React from 'react';
import { RoleSelector } from './RoleSelector';
import { SubroleSelector } from './SubroleSelector';
import { FormField } from './FormField';

export function AccountForm({ 
  account, 
  roles, 
  subroles, 
  onSubmit, 
  onChange, 
  isSubmitting 
}) {
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(account);
  };

  const handleChange = (field, value) => {
    onChange({ ...account, [field]: value });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <FormField
        label="Name"
        value={account.name}
        onChange={(value) => handleChange('name', value)}
        disabled={true} // Name should be read-only
      />

      <RoleSelector
        selectedRole={account.role}
        roles={roles}
        onChange={(value) => handleChange('role', value)}
        disabled={isSubmitting}
      />

      <SubroleSelector
        selectedSubroles={account.subroles || []}
        availableSubroles={subroles}
        onChange={(value) => handleChange('subroles', value)}
        disabled={isSubmitting}
      />

      <div className="flex justify-end">
        <button
          type="submit"
          disabled={isSubmitting}
          className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {isSubmitting ? 'Saving...' : 'Save Changes'}
        </button>
      </div>
    </form>
  );
}