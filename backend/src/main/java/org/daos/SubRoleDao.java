package org.daos;

import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class SubRoleDao {

    public List<SubRole> createSubRoleList() {
        List<SubRole> subRoleList = new ArrayList<>();
        subRoleList.add(new SubRole("Super Manager"));
        subRoleList.add(new SubRole("Department Manager"));
        subRoleList.add(new SubRole("Team Lead"));
        subRoleList.add(new SubRole("Project Manager"));
        subRoleList.add(new SubRole("Regional Manager"));
        return subRoleList;
    }
}