import React, { useState, useMemo } from "react";
import { Pagination } from "../shared/Pagination";
import { SearchBar } from "../shared/SearchBar";
import "../../styles/RoleList.css";

export function RoleList() {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState("");
  const itemsPerPage = 5;

  const [users] = useState([
    {
      id: 1,
      name: "John Doe",
      phone: "(555) 123-4567",
      email: "john@example.com",
      role: "Admin",
    },
    {
      id: 2,
      name: "Jane Smith",
      phone: "(555) 987-6543",
      email: "jane@example.com",
      role: "User",
    },
    {
      id: 3,
      name: "Mike Johnson",
      phone: "(555) 234-5678",
      email: "mike@example.com",
      role: "Manager",
    },
    {
      id: 4,
      name: "Sarah Williams",
      phone: "(555) 876-5432",
      email: "sarah@example.com",
      role: "User",
    },
    {
      id: 5,
      name: "Tom Brown",
      phone: "(555) 345-6789",
      email: "tom@example.com",
      role: "Guest",
    },
    {
      id: 6,
      name: "Emily Davis",
      phone: "(555) 765-4321",
      email: "emily@example.com",
      role: "Manager",
    },
  ]);

  const roles = ["Admin", "User", "Manager", "Guest"];

  const handleRoleChange = (userId, newRole) => {
    // Implementation for role change
    console.log(`Changed role of user ${userId} to ${newRole}`);
  };

  const filteredUsers = useMemo(() => {
    if (!searchQuery) return users;

    const query = searchQuery.toLowerCase();
    return users.filter(
      (user) =>
        user.name.toLowerCase().includes(query) ||
        user.email.toLowerCase().includes(query) ||
        user.phone.toLowerCase().includes(query) ||
        user.role.toLowerCase().includes(query)
    );
  }, [users, searchQuery]);

  React.useEffect(() => {
    setCurrentPage(1);
  }, [searchQuery]);

  const totalPages = Math.ceil(filteredUsers.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentUsers = filteredUsers.slice(startIndex, endIndex);

  return (
    <div className="role-list">
      <div className="role-list-header">
        <h1>User Roles</h1>
        <SearchBar
          value={searchQuery}
          onChange={setSearchQuery}
          placeholder="Search users..."
        />
      </div>

      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Phone Number</th>
              <th>Email</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            {currentUsers.map((user) => (
              <tr key={user.id}>
                <td>{user.name}</td>
                <td>{user.phone}</td>
                <td>{user.email}</td>
                <td>
                  <select
                    value={user.role}
                    onChange={(e) => handleRoleChange(user.id, e.target.value)}
                  >
                    {roles.map((role) => (
                      <option key={role} value={role}>
                        {role}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
            {currentUsers.length === 0 && (
              <tr>
                <td colSpan={4} className="empty-message">
                  No users found matching your search.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="table-footer">
        <div className="entry-count">
          {filteredUsers.length > 0
            ? `Showing ${startIndex + 1}-${Math.min(
                endIndex,
                filteredUsers.length
              )} of ${filteredUsers.length} entries`
            : "No entries to show"}
        </div>
        {filteredUsers.length > 0 && (
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={setCurrentPage}
          />
        )}
      </div>
    </div>
  );
}
