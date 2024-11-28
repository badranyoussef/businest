package org.folder;

import org.daos.FolderDAO;
import org.dtos.FolderDTO;
import org.entities.Folder;
import org.exceptions.ApiException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class FolderService {

    private final FolderDAO folderDAO;

    public FolderService(FolderDAO folderDAO) {
        this.folderDAO = folderDAO;
    }

    // Assign a Role to a Folder
    public void assignRole(String folderId, Role role) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setRole(role);
        folderDAO.update(folder);
    }

    // Assign a SubRole to a Folder
    public void assignSubRole(String folderId, SubRole subRole) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setSubRole(subRole);
        folderDAO.update(folder);
    }

    // Fetch all folders for a given company
    public List<FolderDTO> getFoldersByCompany(String company) {
        List<Folder> folders = folderDAO.findByCompany(company);
        return folders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch a folder by its name and company
    public FolderDTO getFolderByName(String folderName, String company) {
        Folder folder = folderDAO.findByName(folderName, company);

        if (folder == null) {
            throw new ApiException(404, "Folder not found.", Instant.now().toString());
        }

        return convertToDTO(folder);
    }

    // Update folder permissions
    public void updateFolderPermissions(String folderId, FolderDTO updatedFolderDTO) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setRole(updatedFolderDTO.getRole());
        folder.setSubRole(updatedFolderDTO.getSubRole());
        folderDAO.update(folder);
    }

    // Create a new Folder
    public void createFolder(FolderDTO folderDTO) {
        Folder folder = convertToEntity(folderDTO);
        folderDAO.create(folder);
    }

    // Delete a Folder by its ID
    public void deleteFolder(String folderId) {
        folderDAO.delete(folderId);
    }

    // Helper method: Convert Folder entity to FolderDTO
    private FolderDTO convertToDTO(Folder folder) {
        return new FolderDTO(
                folder.getId(),
                folder.getName(),
                folder.getCompany(),
                folder.getRole(),
                folder.getSubRole()
        );
    }

    // Helper method: Convert FolderDTO to Folder entity
    private Folder convertToEntity(FolderDTO folderDTO) {
        Folder folder = new Folder();
        folder.setId(folderDTO.getId());
        folder.setName(folderDTO.getName());
        folder.setCompany(folderDTO.getCompany());
        folder.setRole(folderDTO.getRole());
        folder.setSubRole(folderDTO.getSubRole());
        return folder;
    }
}
