import axios from "axios";

const API_URL = "xxx";

export const getAllFoldersAsync = async (companyName) => {
  const response = await axios.get(`${API_URL}/folders/${companyName}`);
  return response.data;
};

export const getAllRolesAsync = async (company) => {
    const response = await axios.get(`${API_URL}/${company}/roles`);
    return response.data;
}

export const updateFolderRoleAsync = async (folderId, newRole) => {
  const response = await axios.post(`${API_URL}/folders/${folderId}/role`, {
    role: newRole,
  });
  return response.data;
};

export const mockFolders = [
  {
    id: 1,
    folderName: "Project Documentation",
    role: "User",
  },
  {
    id: 2,
    folderName: "Financial Reports",
    role: "User",
  },
  {
    id: 3,
    folderName: "Marketing Assets",
    role: "User",
  },
  {
    id: 4,
    folderName: "Employee Records",
    role: "Manager",
  },
  {
    id: 5,
    folderName: "Client Contracts",
    role: "Manager",
  },
  {
    id: 6,
    folderName: "Product Designs",
    role: "Guest",
  },
];

export const mockRoles = ["Admin", "Manager", "User", "Guest"];