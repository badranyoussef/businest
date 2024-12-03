package org.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "permissions")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "read_permission")
    private boolean readPermission;

    @Column(name = "write_permission")
    private boolean writePermission;

    @Column(name = "delete_permission")
    private boolean deletePermission;

    public Permissions(boolean readPermission, boolean writePermission, boolean deletePermission) {
        this.readPermission = readPermission;
        this.writePermission = writePermission;
        this.deletePermission = deletePermission;
    }
}
