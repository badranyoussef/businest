package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.persistence.model.Permissions;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilePermInFolderDTO {

    private boolean read;
    private boolean write;
    private boolean delete;

    public UserFilePermInFolderDTO(List<Permissions> allPermissions) {

        for ( Permissions p :  allPermissions){

            if (p.isReadPermission()){
                read = true;
            }
            if (p.isWritePermission()){
                write = true;
            }
            if (p.isDeletePermission()){
                delete = true;
            }
        }
    }


}
