import axios from "axios";
import { mockFolders } from "./mockFolderService";

const API_URL = import.meta.env.REACT_APP_API_URL;

export const getFolderByNameAsync = async (folderName) => {
  // const response = await axios.get(`${API_URL}/folders/${folderName}`);
  // return response.data;
  return mockFolders.find((folder) => folder.folderName === folderName);
};

export const getAllFoldersByCompanyNameAsync = async (companyName) => {
  // const response = await axios.get(`${API_URL}/folders/${companyName}`);
  // if (response.data) {
  //   return response.data;
  // }
  return mockFolders;
};

export const getAllRolesByCompanyNameAsync = async (companyName) => {
  const response = await axios.get(`${API_URL}/${companyName}/roles`);
  return response.data;
};

export const updateFolderPermissionsByIdAsync = async (folderId, permissions) => {
  const response = await axios.post(
    `${API_URL}/folders/${folderId}/permissions`,
    {
      permissions,
    }
  );
  return response.data;
};
