import React, { useCallback, useState } from 'react';
import { Upload, X, FileIcon, CheckCircle, AlertCircle } from 'lucide-react';

export default function FileUpload({
  acceptedTypes = ['*'],
  maxSizeMB = 10,
  onFileSelect,
}) {
  const [isDragging, setIsDragging] = useState(false);
  const [file, setFile] = useState(null);
  const [error, setError] = useState('');
  const [preview, setPreview] = useState('');

  const validateFile = (file) => {
    if (maxSizeMB && file.size > maxSizeMB * 1024 * 1024) {
      return `File size must be less than ${maxSizeMB}MB`;
    }
    if (acceptedTypes[0] !== '*' && !acceptedTypes.includes(file.type)) {
      return `File type must be ${acceptedTypes.join(', ')}`;
    }
    return '';
  };

  const handleFile = (file) => {
    const validationError = validateFile(file);
    if (validationError) {
      setError(validationError);
      setFile(null);
      setPreview('');
      return;
    }

    setError('');
    setFile(file);
    onFileSelect?.(file);

    // Create preview for images
    if (file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleDrop = useCallback((e) => {
    e.preventDefault();
    setIsDragging(false);
    
    if (e.dataTransfer.files?.length) {
      handleFile(e.dataTransfer.files[0]);
    }
  }, []);

  const handleDragOver = useCallback((e) => {
    e.preventDefault();
    setIsDragging(true);
  }, []);

  const handleDragLeave = useCallback((e) => {
    e.preventDefault();
    setIsDragging(false);
  }, []);

  const handleFileInput = (e) => {
    if (e.target.files?.length) {
      handleFile(e.target.files[0]);
    }
  };

  const removeFile = () => {
    setFile(null);
    setPreview('');
    setError('');
  };

  return (
    <div className="w-full max-w-md mx-auto">
      <div
        className={`relative border-2 border-dashed rounded-lg p-8 transition-all duration-200 ease-in-out
          ${isDragging ? 'border-blue-500 bg-blue-50' : 'border-gray-300 bg-gray-50'}
          ${error ? 'border-red-500 bg-red-50' : ''}
          ${file ? 'border-green-500 bg-green-50' : ''}`}
        onDrop={handleDrop}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
      >
        <input
          type="file"
          className="hidden"
          onChange={handleFileInput}
          accept={acceptedTypes.join(',')}
          id="fileInput"
        />
        
        {!file && !error && (
          <label
            htmlFor="fileInput"
            className="flex flex-col items-center justify-center cursor-pointer"
          >
            <Upload className="w-12 h-12 text-gray-400 mb-4" />
            <p className="text-lg font-medium text-gray-700 mb-2">
              Drop your file here, or click to select
            </p>
            <p className="text-sm text-gray-500">
              {acceptedTypes[0] === '*' 
                ? 'Supports all file types'
                : `Supports: ${acceptedTypes.join(', ')}`}
            </p>
            <p className="text-sm text-gray-500">
              Max size: {maxSizeMB}MB
            </p>
          </label>
        )}

        {error && (
          <div className="flex items-center justify-center text-red-500">
            <AlertCircle className="w-6 h-6 mr-2" />
            <span>{error}</span>
          </div>
        )}

        {file && !error && (
          <div className="flex items-center justify-between bg-white p-4 rounded-lg shadow-sm">
            <div className="flex items-center space-x-4">
              {preview ? (
                <img
                  src={preview}
                  alt="Preview"
                  className="w-12 h-12 object-cover rounded"
                />
              ) : (
                <FileIcon className="w-12 h-12 text-gray-400" />
              )}
              <div>
                <p className="text-sm font-medium text-gray-700">{file.name}</p>
                <p className="text-xs text-gray-500">
                  {(file.size / (1024 * 1024)).toFixed(2)}MB
                </p>
              </div>
            </div>
            <div className="flex items-center space-x-2">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <button
                onClick={removeFile}
                className="p-1 hover:bg-gray-100 rounded-full transition-colors"
              >
                <X className="w-5 h-5 text-gray-500" />
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
