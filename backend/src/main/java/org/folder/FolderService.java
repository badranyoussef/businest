package org.folder;

import org.daos.CompanyDAO;
import org.daos.FolderDAO;
import org.dtos.FolderDTO;
import org.entities.Company;
import org.entities.Folder;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.exceptions.ApiException;
import jakarta.persistence.EntityManager;

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

    // Assign a RoleFolder to a Folder
    public void assignRole(Long folderId, RoleFolder roleFolder) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setRoleFolder(roleFolder);
        folderDAO.update(folder);
    }

    // Assign a SubRoleFolder to a Folder
    public void assignSubRole(Long folderId, SubRoleFolder subRoleFolder) {
        Folder folder = folderDAO.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException("Folder not found.");
        }

        folder.setSubRoleFolder(subRoleFolder);
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

        folder.setRoleFolder(updatedFolderDTO.getRoleFolder());
        folder.setSubRoleFolder(updatedFolderDTO.getSubRoleFolder());
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
                folder.getRoleFolder(),
                folder.getSubRoleFolder()
        );
    }


    // Helper method: Convert FolderDTO to Folder entity
    private Folder convertToEntity(FolderDTO folderDTO) {
        Folder folder = new Folder();
        folder.setId(folderDTO.getId());
        folder.setName(folderDTO.getName());
        folder.setCompany(folderDTO.getCompany());
        folder.setRoleFolder(folderDTO.getRoleFolder());
        folder.setSubRoleFolder(folderDTO.getSubRoleFolder());
        return folder;
    }

}
