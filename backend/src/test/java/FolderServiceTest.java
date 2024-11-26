import com.github.dockerjava.api.exception.NotFoundException;
import org.daos.FolderDAO;
import org.dtos.FolderDTO;
import org.folder.FolderService;
import org.folder.Role;
import org.folder.SubRole;
import org.junit.jupiter.api.*;
import org.mockito.*;

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
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        FolderDTO folderDTO = new FolderDTO(folderId, "Test Folder", company, Role.GUEST, SubRole.LOW);

        when(folderDAO.findById(folderId)).thenReturn(folderDTO);
        doNothing().when(folderDAO).update(folderDTO);

        // Act
        folderService.assignRole(folderId, company, newRole);

        // Assert
        assertEquals(newRole, folderDTO.getRole());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folderDTO);
    }

    @Test
    void testAssignRole_UnauthorizedAccess() {
        // Arrange
        String folderId = "folder123";
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        FolderDTO folderDTO = new FolderDTO(folderId, "Test Folder", "DifferentCompany", Role.GUEST, SubRole.LOW);

        when(folderDAO.findById(folderId)).thenReturn(folderDTO);

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            folderService.assignRole(folderId, company, newRole);
        });

        assertEquals("Unauthorized access: Cannot modify folders from other companies.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(FolderDTO.class));
    }

    @Test
    void testAssignRole_FolderNotFound() {
        // Arrange
        String folderId = "nonExistentFolder";
        String company = "ExampleCompany";
        Role newRole = Role.USER;

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            folderService.assignRole(folderId, company, newRole);
        });

        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(FolderDTO.class));
    }

    @Test
    void testUpdateSubRole_Success() {
        // Arrange
        String folderId = "folder124";
        String company = "ExampleCompany";
        SubRole newSubRole = SubRole.HIGH;

        FolderDTO folderDTO = new FolderDTO(folderId, "Test Folder", company, Role.USER, SubRole.MEDIUM);

        when(folderDAO.findById(folderId)).thenReturn(folderDTO);
        doNothing().when(folderDAO).update(folderDTO);

        // Act
        folderService.updateSubRole(folderId, company, newSubRole);

        // Assert
        assertEquals(newSubRole, folderDTO.getSubRole());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folderDTO);
    }

    @Test
    void testUpdateSubRole_UnauthorizedAccess() {
        // Arrange
        String folderId = "folder124";
        String company = "ExampleCompany";
        SubRole newSubRole = SubRole.HIGH;

        FolderDTO folderDTO = new FolderDTO(folderId, "Test Folder", "DifferentCompany", Role.USER, SubRole.MEDIUM);

        when(folderDAO.findById(folderId)).thenReturn(folderDTO);

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            folderService.updateSubRole(folderId, company, newSubRole);
        });

        assertEquals("Unauthorized access: Cannot modify folders from other companies.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(FolderDTO.class));
    }

    @Test
    void testUpdateSubRole_FolderNotFound() {
        // Arrange
        String folderId = "nonExistentFolder";
        String company = "ExampleCompany";
        SubRole newSubRole = SubRole.HIGH;

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            folderService.updateSubRole(folderId, company, newSubRole);
        });

        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(FolderDTO.class));
    }
}
