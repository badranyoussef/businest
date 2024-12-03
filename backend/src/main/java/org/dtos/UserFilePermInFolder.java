package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.entities.Permissions;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilePermInFolder {

    private boolean read;
    private boolean write;
    private boolean delete;

    public UserFilePermInFolder(List<Permissions> allPermissions) {

        for(Permissions p : allPermissions){

            if (p.)

        }
        this.read = read;
        this.write = write;
        this.delete = delete;

    }


}
