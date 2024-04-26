package org.belajar.api.services.ojk.Implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.ojkDto.department.DepartmentReqDto;
import org.belajar.api.dto.ojkDto.department.DepartmentResDto;
import org.belajar.api.entity.ojk.Department;
import org.belajar.api.repository.ojk.DepartmentRepository;
import org.belajar.api.services.ojk.DepartmentService;

import java.util.List;

@ApplicationScoped
public class DepartmentServiceImpl implements DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResDto>getAll(){
        return departmentRepository.findAll().stream().map(department -> new DepartmentResDto(department.getDeptId(),department.getDeptName(),department.getDescription(),department.getOjkDepartmentUser())).toList();
    }

    @Override
    @Transactional
    public DepartmentResDto create(DepartmentReqDto departmentReqDto){
        Department department = Department.builder()
                .deptName(departmentReqDto.deptName())
                .description(departmentReqDto.description()).build();

        departmentRepository.persist(department);

        return new DepartmentResDto(department.getDeptId(),department.getDeptName(),department.getDescription(),department.getOjkDepartmentUser());
    }

    @Override
    public Department findById(Long deptId){
        return departmentRepository.findByIdOptional(deptId).orElseThrow(() -> new RuntimeException("department not found"));
    }
}
