import { BASE_URL } from "../utils/globalVariables";

const readAllLogEntries = async () => {
  try {
    const token = localStorage.getItem('token'); 
    if (token) {
      const result = await fetch(`${BASE_URL}/api/logs/all`, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
      });

      const theResult = await result.json();

      return theResult;
    } else {
      throw new Error("No token found");
    }
  } catch (error) {
    console.log("The error: " + error);
    throw error;
  }
};

export { readAllLogEntries };