package org.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
