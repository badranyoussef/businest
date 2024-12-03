package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.entities.Permissions1;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilePermInFolderDTO {

    private boolean read;
    private boolean write;
    private boolean delete;

    public UserFilePermInFolderDTO(List<Permissions1> allPermissions) {

        for ( Permissions1 p :  allPermissions){

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
