import React from 'react';

export function SubroleSelector({ selectedSubroles, availableSubroles, onChange, disabled }) {
  const handleSubroleChange = (subrole) => {
    const updatedSubroles = selectedSubroles.some(selected => selected.title === subrole.title)
      ? selectedSubroles.filter(selected => selected.title !== subrole.title)
      : [...selectedSubroles, subrole];
    onChange(updatedSubroles);
  };

  if (!availableSubroles || availableSubroles.length === 0) {
    return (
      <div className="text-sm text-gray-500 italic">
        No subroles available
      </div>
    );
  }

  return (
    <div className="space-y-2 max-h-48 overflow-y-auto p-2 border border-gray-200 rounded-md">
      {availableSubroles.map((subrole) => (
        <label key={subrole.title} className="flex items-center space-x-2 hover:bg-gray-50 p-1 rounded">
          <input
            type="checkbox"
            checked={selectedSubroles.some(selected => selected.title === subrole.title)}
            onChange={() => handleSubroleChange(subrole)}
            disabled={disabled}
            className="rounded border-gray-300 text-blue-600 focus:ring-blue-500 disabled:opacity-50"
          />
          <span className="text-sm text-gray-700">{subrole.title}</span>
        </label>
      ))}
    </div>
  );
}