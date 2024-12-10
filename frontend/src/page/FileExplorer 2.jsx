import { useState } from 'react';
import {
    Folder,
    File,
    ChevronRight,
    ChevronDown,
    RefreshCw,
    Upload,
    Trash2,
    Plus,
    Search,
    Home,
    Image,
    Download
} from 'lucide-react';

export default function FileExplorer() {
    const [selectedFolder, setSelectedFolder] = useState('root');
    const [expandedFolders, setExpandedFolders] = useState(['root']);

    const menuItems = [
        { id: 'root', icon: Home, label: 'File Explorer' },
        { id: 'documents', icon: Folder, label: 'Documents' },
        { id: 'images', icon: Image, label: 'Images' },
        { id: 'downloads', icon: Download, label: 'Downloads' },
    ];

    const toggleFolder = (folder) => {
        setExpandedFolders(prev =>
            prev.includes(folder)
                ? prev.filter(f => f !== folder)
                : [...prev, folder]
        );
    };

    return (
        <div className="flex h-screen bg-gray-100">
            {/* Left sidebar - Menu */}
            <div className="w-64 bg-white border-r border-gray-200 flex flex-col">
                <div className="p-4 border-b border-gray-200">
                    <h1 className="text-xl font-semibold text-gray-800">Explorer</h1>
                </div>
                <nav className="flex-1 p-4">
                    {menuItems.map((item) => (
                        <button
                            key={item.id}
                            onClick={() => setSelectedFolder(item.id)}
                            className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                                selectedFolder === item.id
                                    ? 'bg-blue-50 text-blue-600'
                                    : 'text-gray-600 hover:bg-gray-50'
                            }`}
                        >
                            <item.icon size={20} />
                            <span className="font-medium">{item.label}</span>
                        </button>
                    ))}
                </nav>
            </div>

            {/* Main content area */}
            <div className="flex-1 flex flex-col">
                {/* Toolbar */}
                <div className="bg-white border-b border-gray-200 p-4">
                    <div className="flex items-center justify-between">
                        <div className="flex items-center space-x-3">
                            <button className="inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                                <Upload size={16} className="mr-2" />
                                Upload
                            </button>
                            <button className="inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                                <Plus size={16} className="mr-2" />
                                New Folder
                            </button>
                            <button className="inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                                <RefreshCw size={16} className="mr-2" />
                                Refresh
                            </button>
                        </div>
                        <div className="relative">
                            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <Search size={16} className="text-gray-400" />
                            </div>
                            <input
                                type="text"
                                placeholder="Search files"
                                className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                            />
                        </div>
                    </div>
                </div>

                {/* File list */}
                <div className="flex-1 overflow-auto bg-white p-4">
                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                        <tr>
                            <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                            <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date modified</th>
                            <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                            <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Size</th>
                            <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                        </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                        {[
                            { name: 'Document.pdf', date: '2024-03-15', type: 'PDF', size: '2.5 MB' },
                            { name: 'Image.jpg', date: '2024-03-14', type: 'Image', size: '1.2 MB' },
                            { name: 'Spreadsheet.xlsx', date: '2024-03-13', type: 'Excel', size: '500 KB' },
                        ].map((file, index) => (
                            <tr key={index} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap">
                                    <div className="flex items-center">
                                        <File size={16} className="mr-2 text-gray-500" />
                                        <span className="text-sm font-medium text-gray-900">{file.name}</span>
                                    </div>
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{file.date}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{file.type}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{file.size}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                    <button className="text-red-500 hover:text-red-600 p-2 rounded-full hover:bg-red-50 transition-colors">
                                        <Trash2 size={16} />
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}