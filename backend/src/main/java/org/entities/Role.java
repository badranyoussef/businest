package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Role {
    private String title;
    //EMPLOYEE, COMPANY_MANAGER, SALES, MARKETING, HR
    @Override
    public String toString() {
        return title;
    }
}
