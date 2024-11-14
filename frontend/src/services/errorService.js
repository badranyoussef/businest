// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function handleApiError(error) {
  // Check if error has a response (server error)
  if (error.response) {
    const status = error.response.status;

    // Handle specific error status codes (like 404, 500)
    if (status === 404) {
      return { message: "Resource not found", status };
    } else if (status === 500) {
      return { message: "Internal server error", status };
    }
  } else if (error.request) {
    // The request was made but no response was received (network issue)
    return {
      message: "Network error. Please check your connection.",
      status: 0,
    };
  }

  // Unknown error (client-side or others)
  return { message: "An unknown error occurred", status: 0 };
}

