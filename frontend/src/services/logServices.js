import { BASE_URL } from "../utils/globalVariables";

const readAllLogs = async () => {
    try {
      const token = localStorage.getItem("token");
  
      if (token) {
        const result = await fetch(`${BASE_URL}/user/logs/`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });
  
        const theResult = await result.json();
  
        return theResult;
      }
    } catch (e) {
      console.log(e);
    }
  };