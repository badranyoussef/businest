import org.daos.FolderDAO;
import org.entities.Folder;
import org.folder.Role;
import org.folder.SubRole;
import org.folder.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FolderServiceTest {

    @Mock
    private FolderDAO folderDAO;

    private FolderService folderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        folderService = new FolderService(folderDAO);
    }

    @Test
    void testAssignRole_Success() {
        // Arrange
        String folderId = "folder123";
        Role newRole = new Role(2L, "USER", null, null);

        Folder folder = new Folder();
        folder.setId(folderId);
        folder.setName("Test Folder");
        folder.setCompany("ExampleCompany");
        folder.setRole(new Role(1L, "GUEST", null, null));

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignRole(folderId, newRole);

        // Assert
        assertEquals(newRole, folder.getRole());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folder);
    }

    @Test
    void testAssignRole_FolderNotFound() {
        // Arrange
        String folderId = "nonExistentFolder";
        Role newRole = new Role(2L, "USER", null, null);

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            folderService.assignRole(folderId, newRole);
        });

        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(Folder.class));
    }

    @Test
    void testAssignSubRole_Success() {
        // Arrange
        String folderId = "folder124";
        SubRole newSubRole = new SubRole(2L, "HIGH", null, null);

        Folder folder = new Folder();
        folder.setId(folderId);
        folder.setName("Test Folder");
        folder.setCompany("ExampleCompany");
        folder.setSubRole(new SubRole(1L, "MEDIUM", null, null));

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignSubRole(folderId, newSubRole);

        // Assert
        assertEquals(newSubRole, folder.getSubRole());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folder);
    }

    @Test
    void testAssignSubRole_FolderNotFound() {
        // Arrange
        String folderId = "nonExistentFolder";
        SubRole newSubRole = new SubRole(2L, "HIGH", null, null);

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            folderService.assignSubRole(folderId, newSubRole);
        });

        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(Folder.class));
    }
}
