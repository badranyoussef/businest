

import org.daos.FolderDAO;
import org.daos.RoleFolderDAO;
import org.daos.SubRoleFolderDAO;
import org.entities.Company;
import org.entities.Folder;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.exceptions.ApiException;
import org.folder.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FolderServiceTest {

    @Mock
    private FolderDAO folderDAO;

    @Mock
    private RoleFolderDAO roleFolderDAO;

    @Mock
    private SubRoleFolderDAO subRoleFolderDAO;

    @InjectMocks
    private FolderService folderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        folderService = new FolderService(folderDAO, null);
    }

    @Test
    void testAssignRole_Success() {
        // Arrange
        Long folderId = 123L;
        RoleFolder newRoleFolder = RoleFolder.builder()
                .id(2L)
                .name("USER")
                .build();

        Company company = Company.builder()
                .id(1L)
                .companyName("ExampleCompany")
                .build();

        Folder folder = Folder.builder()
                .id(folderId)
                .name("Test Folder")
                .company(company)
                .roleFolder(RoleFolder.builder()
                        .id(1L)
                        .name("GUEST")
                        .build())
                .build();

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignRole(folderId, newRoleFolder);

        // Assert
        assertEquals(newRoleFolder.getId(), folder.getRoleFolder().getId());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folder);
    }
    @Test
    void testAssignRole_FolderNotFound() {
        // Arrange
        Long folderId = 999L;
        Company company = Company.builder()
                .id(1L)
                .companyName("ExampleCompany")
                .build();
        RoleFolder newRoleFolder = RoleFolder.builder()
                .id(2L)
                .name("USER")
                .company(company) // Set the company
                .build();

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            folderService.assignRole(folderId, newRoleFolder);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(Folder.class));
    }

    @Test
    void testAssignSubRole_Success() {
        // Arrange
        Long folderId = 124L;
        SubRoleFolder newSubRoleFolder = SubRoleFolder.builder()
                .id(2L)
                .name("HIGH")
                .build();

        Company company = Company.builder()
                .id(1L)
                .companyName("ExampleCompany")
                .build();

        Folder folder = Folder.builder()
                .id(folderId)
                .name("Test Folder")
                .company(company)
                .subRoleFolder(SubRoleFolder.builder()
                        .id(1L)
                        .name("MEDIUM")
                        .build())
                .build();

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignSubRole(folderId, newSubRoleFolder);

        // Assert
        assertEquals(newSubRoleFolder.getId(), folder.getSubRoleFolder().getId());
        verify(folderDAO).findById(folderId);
        verify(folderDAO).update(folder);
    }

    @Test
    void testAssignSubRole_FolderNotFound() {
        // Arrange
        Long folderId = 999L;
        Company company = Company.builder()
                .id(1L)
                .companyName("ExampleCompany")
                .build();
        SubRoleFolder newSubRoleFolder = SubRoleFolder.builder()
                .id(2L)
                .name("HIGH")
                .company(company) // Set the company
                .build();

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            folderService.assignSubRole(folderId, newSubRoleFolder);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(Folder.class));
    }

}
