package org.belajar.api.services.ojk;

import org.belajar.api.dto.ojkDto.department.DepartmentReqDto;
import org.belajar.api.dto.ojkDto.department.DepartmentResDto;
import org.belajar.api.entity.ojk.Department;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResDto> getAll();
    DepartmentResDto create(DepartmentReqDto departmentReqDto);
    Department findById(Long deptId);
}
