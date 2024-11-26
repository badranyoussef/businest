package org.daos;

import com.github.dockerjava.api.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.folder.Folder;
import org.dtos.FolderDTO;
import java.util.List;
import java.util.stream.Collectors;

public class FolderDAO {

    private EntityManagerFactory emf;

    public FolderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(FolderDTO folderDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = convertToEntity(folderDTO);
            em.persist(folder);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Update method using FolderDTO
    public void update(FolderDTO folderDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = convertToEntity(folderDTO);
            em.merge(folder);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Find method returning FolderDTO
    public FolderDTO findById(String folderId) {
        EntityManager em = emf.createEntityManager();
        try {
            Folder folder = em.find(Folder.class, folderId);
            return folder != null ? convertToDTO(folder) : null;
        } finally {
            em.close();
        }
    }

    // List method returning List<FolderDTO>
    public List<FolderDTO> findByCompany(String company) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT f FROM Folder f WHERE f.company = :company";
            List<Folder> folders = em.createQuery(jpql, Folder.class)
                    .setParameter("company", company)
                    .getResultList();
            return folders.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }


    // Delete Folder
    public void delete(String folderId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = em.find(Folder.class, folderId);
            if (folder != null) {
                em.remove(folder);
                em.getTransaction().commit();
            } else {
                throw new NotFoundException("Folder with ID " + folderId + " not found.");
            }
        } finally {
            em.close();
        }
    }


    private FolderDTO convertToDTO(Folder folder) {
        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setId(folder.getId());
        folderDTO.setName(folder.getName());
        folderDTO.setCompany(folder.getCompany());
        folderDTO.setRole(folder.getRole());
        folderDTO.setSubRole(folder.getSubRole());
        return folderDTO;
    }

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
