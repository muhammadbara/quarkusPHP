package org.belajar.api.dto.ojkDto.department;

import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;

import java.util.List;

public record DepartmentResDto(Long deptId, String deptName, String description, List<OjkDepartmentUser> ojkDepartmentUser) {
}
