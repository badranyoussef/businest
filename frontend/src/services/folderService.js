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

export const getAllFilesInFolderAsync = async (folderId) => {
  //Insert correct URL for fetching all files in a folder 
  const response = await axios.get(`${API_URL}/folders/${folderId}`);
  return response.data;
};

const mockFolderPermissions1 = {
  read: true,
  write: false,
  delete: false,
  download: false,
  manageFolder: false,
  manageFiles: false,
};

const mockFolderPermissions2 = {
  read: true,
  write: true,
  delete: false,
  download: true,
  manageFolder: false,
  manageFiles: false,
};

const mockFolderPermissions3 = {
  read: true,
  write: true,
  delete: true,
  download: true,
  manageFolder: true,
  manageFiles: true,
};

const mockFolderPermissions4 = {
  read: false,
  write: false,
  delete: false,
  download: false,
  manageFolder: false,
  manageFiles: false,
};

const mockFolderPermissions5 = {
  read: true,
  write: false,
  delete: false,
  download: false,
  manageFolder: false,
  manageFiles: false,
};

const mockFolderPermissions6 = {
  read: true,
  write: true,
  delete: true,
  download: false,
  manageFolder: false,
  manageFiles: false,
};

export const mockFolders = [
  {
    id: 1,
    folderName: "Project Documentation",
    rolesPermissionsMatrix: {
      CEO: {
        basic: mockFolderPermissions1,
        lead: mockFolderPermissions2,
      },
      CFO: {
        lead: mockFolderPermissions2,
        guest: mockFolderPermissions1,
      },
      COO: {
        guest: mockFolderPermissions3,
        basic: mockFolderPermissions4,
      },
    },
    numberOfFiles: 45,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 2,
    folderName: "Financial Reports",
    rolesPermissionsMatrix: {
      CFO: {
        lead: mockFolderPermissions1,
        guest: mockFolderPermissions4,
      },
      HR: {
        basic: mockFolderPermissions4,
        lead: mockFolderPermissions3,
      },
    },
    numberOfFiles: 88,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 3,
    folderName: "Marketing Assets",
    rolesPermissionsMatrix: {
      COO: {
        basic: mockFolderPermissions2,
        lead: mockFolderPermissions5,
      },
      HR: {
        lead: mockFolderPermissions5,
        guest: mockFolderPermissions6,
      },
      CEO: {
        guest: mockFolderPermissions6,
        lead: mockFolderPermissions3,
      },
    },
    numberOfFiles: 32,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 4,
    folderName: "Employee Records",
    rolesPermissionsMatrix: {
      HR: {
        lead: mockFolderPermissions3,
        basic: mockFolderPermissions2,
      },
      CFO: {
        basic: mockFolderPermissions2,
        lead: mockFolderPermissions5,
      },
    },
    numberOfFiles: 60,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 5,
    folderName: "Client Contracts",
    rolesPermissionsMatrix: {
      CEO: {
        basic: mockFolderPermissions1,
        lead: mockFolderPermissions5,
      },
      COO: {
        guest: mockFolderPermissions4,
        basic: mockFolderPermissions6,
      },
    },
    numberOfFiles: 25,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
  {
    id: 6,
    folderName: "Product Designs",
    rolesPermissionsMatrix: {
      CFO: {
        lead: mockFolderPermissions2,
        guest: mockFolderPermissions1,
      },
      HR: {
        basic: mockFolderPermissions5,
        lead: mockFolderPermissions3,
      },
    },
    numberOfFiles: 73,
    lastUpdated: new Date(
      Date.now() - Math.floor(Math.random() * 7) * 24 * 60 * 60 * 1000
    ),
  },
];

export const mockRoles = ["CEO", "CFO", "COO", "HR"];

export const mockSubroles = ["guest", "basic", "lead"];

// Mock fetching folder details by ID
export const getFolderByNameAsync = async (folderName) => {
  return mockFolders.find((folder) => folder.folderName === folderName);
};

// Mock updating folder permissions
export const updateFolderPermissionsAsync = async (folderId, permissions) => {
  console.log(`Updated permissions for folder ${folderId}:`, permissions);
};
