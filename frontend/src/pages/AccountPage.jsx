import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import { getAccountById, updateAccountRole, getRolesAndSubroles } from '../api/accountApi';
import { AccountForm } from '../components/AccountForm';
import { LoadingSpinner } from '../components/LoadingSpinner';
import { ErrorMessage } from '../components/ErrorMessage';

export function AccountPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [account, setAccount] = useState(null);
  const [roles, setRoles] = useState([]);
  const [subRoles, setSubRoles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [updateMessage, setUpdateMessage] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [accountData, rolesData] = await Promise.all([
          getAccountById(id),
          getRolesAndSubroles()
        ]);
        
        setAccount(accountData);
        setRoles(rolesData.roles);
        setSubRoles(rolesData.subRoles || []);
        console.log('testing data');
        console.log(rolesData);
      } catch (err) {
        setError('Failed to load account data');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (updatedAccount) => {
    setIsSubmitting(true);
    setError(null);
    setUpdateMessage('');

    try {
      await updateAccountRole(updatedAccount);
      setUpdateMessage('Account updated successfully');
      setTimeout(() => setUpdateMessage(''), 3000);
    } catch (err) {
      setError('Failed to update account');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <LoadingSpinner />
      </div>
    );
  }

  if (!account) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <ErrorMessage message="Account not found" />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-3xl mx-auto">
        <button
          onClick={() => navigate('/account')}
          className="mb-6 inline-flex items-center text-blue-600 hover:text-blue-500"
        >
          <ArrowLeft className="h-5 w-5 mr-1" />
          Back to Accounts
        </button>

        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-800">Edit Account</h2>
          </div>

          <div className="px-6 py-4">
            {error && <ErrorMessage message={error} />}
            
            {updateMessage && (
              <div className="mb-4 p-4 bg-green-50 text-green-700 rounded-md">
                {updateMessage}
              </div>
            )}

            <AccountForm
              account={account}
              roles={roles}
              subRoles={subRoles}
              onSubmit={handleSubmit}
              onChange={setAccount}
              isSubmitting={isSubmitting}
            />
          </div>
        </div>
      </div>
    </div>
  );
}