package org.folder;

import com.github.dockerjava.api.exception.NotFoundException;
import org.daos.FolderDAO;

import org.dtos.FolderDTO;

import java.util.List;

public class FolderService {

    private final FolderDAO folderDAO;

    public FolderService(FolderDAO folderDAO) {
        this.folderDAO = folderDAO;
    }

    public void assignRole(String folderId, String company, Role newRole) {
        FolderDTO folderDTO = folderDAO.findById(folderId);

        if (folderDTO == null) {
            throw new NotFoundException("Folder not found.");
        }

        if (!folderDTO.getCompany().equals(company)) {
            throw new SecurityException("Unauthorized access: Cannot modify folders from other companies.");
        }

        folderDTO.setRole(newRole);
        folderDAO.update(folderDTO);
    }

    public List<FolderDTO> getFoldersInCompany(String company) {
        return folderDAO.findByCompany(company);
    }

    public void updateSubRole(String folderId, String company, SubRole subRole) {
        FolderDTO folderDTO = folderDAO.findById(folderId);

        if (folderDTO == null) {
            throw new NotFoundException("Folder not found.");
        }

        if (!folderDTO.getCompany().equals(company)) {
            throw new SecurityException("Unauthorized access: Cannot modify folders from other companies.");
        }

        folderDTO.setSubRole(subRole);
        folderDAO.update(folderDTO);
    }

    public void createFolder(FolderDTO folderDTO) {
        folderDAO.create(folderDTO);
    }

    public void deleteFolder(String folderId) {
        FolderDTO folderDTO = folderDAO.findById(folderId);
        if (folderDTO == null) {
            throw new NotFoundException("Folder not found.");
        }
        folderDAO.delete(folderId);
    }
}
