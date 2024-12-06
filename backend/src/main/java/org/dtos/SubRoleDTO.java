package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubRoleDTO {
    private String title;

    @Override
    public String toString() {
        return title;
    }
}
