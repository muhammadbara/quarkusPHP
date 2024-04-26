package org.belajar.api.services.ojk;

import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;

import java.util.List;

public interface OjkDepartmentUserService {
    void processOjkDepartmentUsers(List<OjkDepartmentUser> ojkDepartmentUsers, Ojk ojk);
    List<OjkDepartmentUser> editOjkDepartmentUser(List<OjkDepartmentUser> updatedOjkDepartmentUsers, Ojk ojk);

}
