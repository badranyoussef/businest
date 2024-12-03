package org.daos;

import org.entities.Role;
import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public List<SubRole> getUserSubRoles(int userId){

        List<SubRole> subRolesOfUser = new ArrayList<>();
        Role role1 = getRoleOfUser(userId);
        SubRole subRole1 = new SubRole("Sub1", role1);
        SubRole subRole2 = new SubRole("Sub2", role1);

        subRolesOfUser.add(subRole1);
        subRolesOfUser.add(subRole2);

        return subRolesOfUser;

    }

    public Role getRoleOfUser (int userId){

        Role role = new Role();

        return role;
    }

}
