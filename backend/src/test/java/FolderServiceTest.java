

import org.daos.FolderDAO;
import org.daos.RoleDAO;
import org.daos.SubRoleDAO;
import org.entities.Company;
import org.entities.Folder;
import org.entities.Role;
import org.entities.SubRole;
import org.exceptions.ApiException;
import org.folder.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FolderServiceTest {

    @Mock
    private FolderDAO folderDAO;

    @Mock
    private RoleDAO roleDAO;

    @Mock
    private SubRoleDAO subRoleDAO;

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
        Role newRole = Role.builder()
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
                .role(Role.builder()
                        .id(1L)
                        .name("GUEST")
                        .build())
                .build();

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignRole(folderId, newRole);

        // Assert
        assertEquals(newRole.getId(), folder.getRole().getId());
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
        Role newRole = Role.builder()
                .id(2L)
                .name("USER")
                .company(company) // Set the company
                .build();

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            folderService.assignRole(folderId, newRole);
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
        SubRole newSubRole = SubRole.builder()
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
                .subRole(SubRole.builder()
                        .id(1L)
                        .name("MEDIUM")
                        .build())
                .build();

        when(folderDAO.findById(folderId)).thenReturn(folder);
        doNothing().when(folderDAO).update(folder);

        // Act
        folderService.assignSubRole(folderId, newSubRole);

        // Assert
        assertEquals(newSubRole.getId(), folder.getSubRole().getId());
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
        SubRole newSubRole = SubRole.builder()
                .id(2L)
                .name("HIGH")
                .company(company) // Set the company
                .build();

        when(folderDAO.findById(folderId)).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            folderService.assignSubRole(folderId, newSubRole);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("Folder not found.", exception.getMessage());
        verify(folderDAO).findById(folderId);
        verify(folderDAO, never()).update(any(Folder.class));
    }

}
