import React, { useState, useMemo } from 'react';
import { Users } from 'lucide-react';
import { SearchBar } from '../components/SearchBar';
import { EmployeeTable } from '../components/EmployeeTable';
import { getEmployees, mockEmployees } from '../data/mockEmployees';

export function AccountManagementPage() {
  const [searchQuery, setSearchQuery] = useState('');

  const filteredEmployees = useMemo(() => {
    return getEmployees.filter(employee =>
      employee.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      employee.role.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }, [searchQuery]);

  return (
    <div className="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <div className="flex items-center">
            <Users className="h-8 w-8 text-blue-600 mr-3" />
            <h1 className="text-2xl font-bold text-gray-900">Account Management</h1>
          </div>
          <div className="w-72">
            <SearchBar value={searchQuery} onChange={setSearchQuery} />
          </div>
        </div>
        
        <EmployeeTable employees={filteredEmployees} />
      </div>
    </div>
  );
}