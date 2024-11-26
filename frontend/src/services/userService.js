import axios from "axios";

export const getAllFavoritesByUserIdAsync = async (userId) => {
  // const response = await axios.get(`${API_URL}/users/${userId}/favorites`);
  // return response.data;

  return [
    {
      folderId: 1,
      folderName: "Folder 1",
    },
    {
      folderId: 2,
      folderName: "Folder 2",
    },
    {
      folderId: 3,
      folderName: "Folder 3",
    },
  ];
};

export const getAllImportantByUserIdAsync = async (userId) => {
  // const response = await axios.get(`${API_URL}/users/${userId}/important`);
  // return response.data;

  return [
    {
      folderId: 1,
      folderName: "Folder 1",
    },
    {
      folderId: 2,
      folderName: "Folder 2",
    },
    {
      folderId: 3,
      folderName: "Folder 3",
    },
  ];
};
