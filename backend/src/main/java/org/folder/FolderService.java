package org.folder;

import org.daos.CompanyDAO;
import org.daos.FolderDAO;
import org.dtos.FolderDTO;
import org.entities.Company;
import org.entities.Folder;
import org.entities.Role;
import org.entities.SubRole;
import org.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.persistence.HibernateConfig.entityManagerFactory;

public class FolderService {

    private final FolderDAO folderDAO;
    private final CompanyDAO companyDAO;

    public FolderService(FolderDAO folderDAO, CompanyDAO companyDAO) {
        this.folderDAO = folderDAO;
        this.companyDAO = companyDAO;
    }

    // Assign a Role to a Folder
    public void assignRole(Long folderId, Role role) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setRole(role);
        folderDAO.update(folder);
    }

    // Assign a SubRole to a Folder
    public void assignSubRole(Long folderId, SubRole subRole) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setSubRole(subRole);
        folderDAO.update(folder);
    }

    public List<FolderDTO> getFoldersByCompany(String companyName) {
        // Fetch the Company object using companyName
        Company company = companyDAO.findByName(companyName);
        if (company == null) {
            throw new ApiException(404, "Company not found.", Instant.now().toString());
        }

        // Fetch folders associated with the company
        List<Folder> folders = folderDAO.findByCompany(company);
        return folders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public FolderDTO getFolderByName(String folderName, String companyName) {
        Folder folder = folderDAO.findByNameAndCompanyName(folderName, companyName);

        if (folder == null) {
            throw new ApiException(404, "Folder not found.", Instant.now().toString());
        }

        return convertToDTO(folder);
    }


    // Update folder permissions
    public void updateFolderPermissions(Long folderId, FolderDTO updatedFolderDTO) {
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
    public void deleteFolder(Long folderId) {
        folderDAO.delete(folderId);
    }

    public List<FolderDTO> getFoldersByCompanyId(Long companyId) {
        List<Folder> folders = folderDAO.findByCompanyId(companyId);
        return folders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Company findById(Long companyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Company.class, companyId);
        } finally {
            entityManager.close();
        }
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
