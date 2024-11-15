import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, Save, UserCog } from 'lucide-react';
import { getAccounts, updateAccount } from '../api/accountApi';

const ROLES = {
  COMPANY_MANAGER: 'Company Manager',
  EMPLOYEE: 'Employee',
  ADMIN: 'Admin'
};

export function AccountPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [employee, setEmployee] = useState(null);
  const [role, setRole] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    const fetchEmployee = async () => {
      try {
        const response = await getAccounts();
        const emp = response.find(e => e.id === Number(id));
        if (emp) {
          setEmployee(emp);
          setRole(emp.role || ROLES.EMPLOYEE); // Set default role if none exists
        } else {
          setError('Employee not found');
        }
      } catch (err) {
        console.error('Error fetching employee:', err);
        setError('Failed to fetch employee details');
      } finally {
        setLoading(false);
      }
    };

    fetchEmployee();
  }, [id]);

  const handleSave = async () => {
    try {
      setSaving(true);
      const updatedEmployee = { ...employee, role };
      await updateAccount(id, updatedEmployee);
      setEmployee(updatedEmployee);
      setIsEditing(false);
      alert('Role updated successfully!');
    } catch (err) {
      console.error('Error updating role:', err);
      alert('Failed to update role');
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (error || !employee) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="bg-red-50 text-red-800 p-4 rounded-lg">
          {error || 'Employee not found'}
        </div>
      </div>
    );
  }

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
          <div className="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
            <h2 className="text-xl font-semibold text-gray-800">Employee Account Details</h2>
            <UserCog className="h-6 w-6 text-blue-600" />
          </div>

          <div className="px-6 py-4 space-y-6">
            <div className="grid grid-cols-2 gap-6">
              {employee.name && (
                <div>
                  <label className="block text-sm font-medium text-gray-700">Name</label>
                  <p className="mt-1 text-gray-900">{employee.name}</p>
                </div>
              )}
              {employee.email && (
                <div>
                  <label className="block text-sm font-medium text-gray-700">Email</label>
                  <p className="mt-1 text-gray-900">{employee.email}</p>
                </div>
              )}
              {employee.department && (
                <div>
                  <label className="block text-sm font-medium text-gray-700">Department</label>
                  <p className="mt-1 text-gray-900">{employee.department}</p>
                </div>
              )}
              {employee.joinDate && (
                <div>
                  <label className="block text-sm font-medium text-gray-700">Join Date</label>
                  <p className="mt-1 text-gray-900">{employee.joinDate}</p>
                </div>
              )}
              {employee.salary && (
                <div>
                  <label className="block text-sm font-medium text-gray-700">Salary</label>
                  <p className="mt-1 text-gray-900">${employee.salary.toLocaleString()}</p>
                </div>
              )}
            </div>

            <div className="bg-blue-50 p-4 rounded-lg">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-medium text-blue-900">Role Management</h3>
                {!isEditing && (
                  <button
                    onClick={() => setIsEditing(true)}
                    className="text-blue-600 hover:text-blue-500 text-sm font-medium"
                  >
                    Edit Role
                  </button>
                )}
              </div>
              
              <div className="space-y-4">
                {isEditing ? (
                  <>
                    <select
                      value={role}
                      onChange={(e) => setRole(e.target.value)}
                      className="block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all duration-200"
                      disabled={saving}
                    >
                      <option value={ROLES.COMPANY_MANAGER}>{ROLES.COMPANY_MANAGER}</option>
                      <option value={ROLES.EMPLOYEE}>{ROLES.EMPLOYEE}</option>
                      <option value={ROLES.ADMIN}>{ROLES.ADMIN}</option>
                    </select>
                    <div className="flex space-x-3">
                      <button
                        onClick={handleSave}
                        disabled={saving}
                        className="flex-1 inline-flex justify-center items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        <Save className="h-4 w-4 mr-2" />
                        {saving ? 'Saving...' : 'Save Changes'}
                      </button>
                      <button
                        onClick={() => {
                          setIsEditing(false);
                          setRole(employee.role || ROLES.EMPLOYEE);
                        }}
                        disabled={saving}
                        className="flex-1 inline-flex justify-center items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        Cancel
                      </button>
                    </div>
                  </>
                ) : (
                  <div className="bg-white p-3 rounded-md border border-blue-100">
                    <p className="text-sm text-gray-600">Current Role</p>
                    <p className="text-lg font-medium text-blue-900 mt-1">{role || 'No role assigned'}</p>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}