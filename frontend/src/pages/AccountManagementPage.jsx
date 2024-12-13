import React, { useState, useEffect, useMemo } from 'react';
import { Users } from 'lucide-react';
import { SearchBar } from '../components/SearchBar';
import { EmployeeTable } from '../components/EmployeeTable';
import { LoadingSpinner } from '../components/LoadingSpinner';
import { ErrorMessage } from '../components/ErrorMessage';
import { getAccounts } from '../api/accountApi';

export function AccountManagementPage() {
  const [searchQuery, setSearchQuery] = useState('');
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const data = await getAccounts();
        console.log(data);
        setEmployees(data);
      } catch (err) {
        setError('Failed to fetch employees');
      } finally {
        setLoading(false);
      }
    };

    fetchEmployees();
  }, []);

  const filteredEmployees = useMemo(() => {
    return employees.filter(employee => {
      const name = employee.name || ''; // Use an empty string if name is undefined
      const roles = employee.roles || []; // Use an empty array if roles is undefined
      console.log(employee)
      return (
        name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        roles.some(role =>
          role.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
          (role.subRoles || []).some(subrole =>
            subrole.title.toLowerCase().includes(searchQuery.toLowerCase())
          )
        )
      );
    });
  }, [searchQuery, employees]);

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <LoadingSpinner />
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <ErrorMessage message={error} />
      </div>
    );
  }

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