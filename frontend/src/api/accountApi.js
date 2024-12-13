import axios from 'axios';

const API_URL = 'http://localhost:7007/api/account';

export const XXXXXXXXXXgetAccounts = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data.map(account => ({
      ...account,
      subRoles: Array.isArray(account.subRoles) ? account.subRoles : []
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
      subRoles: Array.isArray(response.data.subRoles) ? response.data.subRoles : []
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
    // const roles = Array.isArray(response.data.roles) 
    //   ? response.data.roles.map(role => role.title) 
    //   : [];
    
    // const subRoles = Array.isArray(response.data.subRoles) 
    //   ? response.data.subRoles.map(subrole => subrole.title) 
    //   : [];
    return response.data;
    // return {
    //   roles,
    //   subRoles // Now it's a flat array of all available subroles
    // };
  } catch (error) {
    console.error('Error fetching roles and subRoles:', error);
    throw error;
  }
};