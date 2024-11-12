

import org.folder.Folder;
import org.folder.FolderService;
import org.folder.Role;
import org.junit.jupiter.api.*;
import org.mockito.*;
import javax.persistence.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FolderServiceTest {

    private FolderService folderService;

    @Mock
    private EntityManagerFactory emf;

    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(transaction);

        // Mock transaction.isActive() to return true
        when(transaction.isActive()).thenReturn(true);

        folderService = new FolderService(emf);
    }

    @Test
    void testAssignRole_Success() {
        // Arrange
        String folderId = "folder123";
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        Folder folder = new Folder(folderId, "Test Folder", company, Role.GUEST);

        when(em.find(Folder.class, folderId)).thenReturn(folder);

        // Act
        folderService.assignRole(folderId, company, newRole);

        // Assert
        assertEquals(newRole, folder.getRole());
        verify(em).merge(folder);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testAssignRole_UnauthorizedAccess() {
        // Arrange
        String folderId = "folder123";
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        // The folder belongs to a different company
        Folder folder = new Folder(folderId, "Test Folder", "DifferentCompany", Role.GUEST);

        when(em.find(Folder.class, folderId)).thenReturn(folder);

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            folderService.assignRole(folderId, company, newRole);
        });

        assertEquals("Unauthorized access: Cannot modify folders from other companies.", exception.getMessage());
        verify(transaction).begin();
        verify(transaction).rollback();
    }

    @Test
    void testAssignRole_FolderNotFound() {
        // Arrange
        String folderId = "nonExistentFolder";
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        when(em.find(Folder.class, folderId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            folderService.assignRole(folderId, company, newRole);
        });

        assertEquals("Folder not found.", exception.getMessage());
        verify(transaction).begin();
        verify(transaction).rollback();
    }
}
