import React from 'react';
import { TableHeader } from './TableHeader';
import { TableRow } from './TableRow';

export function EmployeeTable({ employees }) {
  
  if (!employees?.length) {
    return (
      <div className="bg-white rounded-lg shadow p-6 text-center text-gray-500">
        No employees found
      </div>
    );
  }

  return (
    <div className="overflow-x-auto bg-white rounded-lg shadow">
      <table className="min-w-full divide-y divide-gray-200">
        <TableHeader />
        <tbody className="bg-white divide-y divide-gray-200">
          {employees.map((employee) => (
            <TableRow key={employee.id} employee={employee} />
          ))
          }
        </tbody>
      </table>
    </div>
  );
}