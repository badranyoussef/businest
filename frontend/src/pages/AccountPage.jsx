import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, Save } from 'lucide-react';
import { mockEmployees } from '../data/mockEmployees';
import { ROLES } from '../data/roles';

export function AccountPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [employee, setEmployee] = useState(null);
  const [role, setRole] = useState('');

  useEffect(() => {
    const emp = mockEmployees.find(e => e.id === Number(id));
    if (emp) {
      setEmployee(emp);
      setRole(emp.role);
    }
  }, [id]);

  if (!employee) {
    return <div>Employee not found</div>;
  }

  const handleSave = () => {
    // In a real app, this would make an API call
    setEmployee(prev => prev ? { ...prev, role } : null);
    alert('Role updated successfully!');
  };

  return (
    <div className="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-3xl mx-auto">
        <button
          onClick={() => navigate('/accounts')}
          className="mb-6 inline-flex items-center text-blue-600 hover:text-blue-500"
        >
          <ArrowLeft className="h-5 w-5 mr-1" />
          Back to Accounts
        </button>

        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-800">Employee Account Details</h2>
          </div>

          <div className="px-6 py-4 space-y-6">
            <div className="grid grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-700">Name</label>
                <p className="mt-1 text-gray-900">{employee.name}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Email</label>
                <p className="mt-1 text-gray-900">{employee.email}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Department</label>
                <p className="mt-1 text-gray-900">{employee.department}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Join Date</label>
                <p className="mt-1 text-gray-900">{employee.joinDate}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Salary</label>
                <p className="mt-1 text-gray-900">${employee.salary.toLocaleString()}</p>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Role</label>
                <select
                  value={role}
                  onChange={(e) => setRole(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all duration-200"
                >
                  <option value={ROLES.COMPANY_MANAGER}>{ROLES.COMPANY_MANAGER}</option>
                  <option value={ROLES.EMPLOYEE}>{ROLES.EMPLOYEE}</option>
                  <option value={ROLES.ADMIN}>{ROLES.ADMIN}</option>
                </select>
              </div>
            </div>

            <div className="flex justify-end">
              <button
                onClick={handleSave}
                className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                <Save className="h-4 w-4 mr-2" />
                Save Changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}