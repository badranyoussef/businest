import axios from 'axios';

const API_URL = 'http://localhost:7007/api/account';

export const XXXXXXXXXXgetAccounts = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data.map(account => ({
      ...account,
      subroles: Array.isArray(account.subroles) ? account.subroles : []
    }));
  } catch (error) {
    console.error('Error fetching accounts:', error);
    throw error;
  }
};

export const getAccounts = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching accounts:', error);
    throw error;
  }
};

export const getAccountById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return {
      ...response.data,
      subroles: Array.isArray(response.data.subroles) ? response.data.subroles : []
    };
  } catch (error) {
    console.error('Error fetching account:', error);
    throw error;
  }
};

export const updateAccountRole = async (accountData) => {
  try {
    const response = await axios.put(`${API_URL}/update_role`, accountData);
    return response.data;
  } catch (error) {
    console.error('Error updating account role:', error);
    throw error;
  }
};

export const getRolesAndSubroles = async () => {
  try {
    const response = await axios.get(`${API_URL}/roles_subroles`);
    // Transform the API response to match our needs
    const roles = Array.isArray(response.data.roles) 
      ? response.data.roles.map(role => role.title) 
      : [];
    
    const subroles = Array.isArray(response.data.subroles) 
      ? response.data.subroles.map(subrole => subrole.title) 
      : [];
    
    return {
      roles,
      subroles // Now it's a flat array of all available subroles
    };
  } catch (error) {
    console.error('Error fetching roles and subroles:', error);
    throw error;
  }
};