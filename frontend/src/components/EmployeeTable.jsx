import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye } from 'lucide-react';

export function EmployeeTable({ employees }) {
  const navigate = useNavigate();

  return (
    <div className="overflow-x-auto bg-white rounded-lg shadow">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Subroles</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {employees.map((employee) => (
            <tr key={employee.id} className="hover:bg-gray-50 transition-colors duration-200">
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{employee.id}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{employee.name}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{employee.role}</td>
              <td className="px-6 py-4 text-sm text-gray-900">
                <div className="flex flex-wrap gap-1">
                  {employee.subRoles?.map((subrole) => (
                    <span
                      key={subrole}
                      className="inline-block px-2 py-1 text-xs font-medium bg-blue-100 text-blue-800 rounded-full"
                    >
                      {subrole}
                    </span>
                  ))}
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm">
                <button
                  onClick={() => navigate(`/account/${employee.id}`)}
                  className="inline-flex items-center px-3 py-1 border border-transparent text-sm leading-5 font-medium rounded-md text-blue-600 hover:text-blue-500 focus:outline-none focus:border-blue-300 focus:ring focus:ring-blue-200 active:text-blue-800 transition-colors duration-200"
                >
                  <Eye className="h-4 w-4 mr-1" />
                  View Account
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}