import axios from "axios";

const API_URL = import.meta.env.REACT_APP_API_URL;

export const getAllFoldersAsync = async (companyName) => {
  const response = await axios.get(`${API_URL}/folders/${companyName}`);
  return response.data;
};

export const getAllRolesAsync = async (company) => {
  const response = await axios.get(`${API_URL}/${company}/roles`);
  return response.data;
};

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
    roles: ["User", "Admin"],
    numberOfFiles: 45,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ), // Random date within the last week
  },
  {
    id: 2,
    folderName: "Financial Reports",
    roles: ["User"],
    numberOfFiles: 88,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 3,
    folderName: "Marketing Assets",
    roles: ["User", "Manager"],
    numberOfFiles: 32,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 4,
    folderName: "Employee Records",
    roles: ["Manager", "Admin"],
    numberOfFiles: 60,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 5,
    folderName: "Client Contracts",
    roles: ["Manager", "User"],
    numberOfFiles: 25,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 6,
    folderName: "Product Designs",
    roles: ["Guest", "User"],
    numberOfFiles: 73,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
];

export const mockRoles = ["Admin", "Manager", "User", "Guest"];

export const mockSubroles = ["guest", "basic", "lead"];

export const mockFolderPermissions = {
  guest: {
    read: true,
    write: false,
    delete: false,
    download: false,
    manageFolder: false,
    manageFiles: false,
  },
  basic: {
    read: true,
    write: true,
    delete: false,
    download: true,
    manageFolder: false,
    manageFiles: false,
  },
  lead: {
    read: true,
    write: true,
    delete: true,
    download: true,
    manageFolder: true,
    manageFiles: true,
  },
};

// Mock fetching folder details by ID
export const getFolderByIdAsync = async (folderName) => {
  return mockFolders.find((folder) => folder.folderName === folderName);
};

// Mock updating folder permissions
export const updateFolderPermissionsAsync = async (folderId, permissions) => {
  console.log(`Updated permissions for folder ${folderId}:`, permissions);
};
