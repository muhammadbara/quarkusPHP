package org.belajar.serviceTest.ojk;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.dto.ojkDto.department.DepartmentReqDto;
import org.belajar.api.dto.ojkDto.department.DepartmentResDto;
import org.belajar.api.entity.ojk.Department;
import org.belajar.api.repository.ojk.DepartmentRepository;
import org.belajar.api.services.ojk.Implementation.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
public class DepartmentServiceImplTest {
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        // Mock data
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "Dept1", "Description1", null));
        departments.add(new Department(2L, "Dept2", "Description2", null));

        List<DepartmentResDto> departmentResDto = departments.stream()
                .map(department -> new DepartmentResDto(department.getDeptId(), department.getDeptName(), department.getDescription(), department.getOjkDepartmentUser()))
                .toList();

        PanacheQuery<Department> panacheQuery = Mockito.mock(PanacheQuery.class);

        when(panacheQuery.stream()).thenReturn(departments.stream());
        when(departmentRepository.findAll()).thenReturn(panacheQuery);

        // Test
        List<DepartmentResDto> result = departmentService.getAll();

        // Verify
        assertEquals(departmentResDto.size(), result.size());
        assertEquals(departmentResDto.get(0).deptName(), result.get(0).deptName());
        assertEquals(departmentResDto.get(1).description(), result.get(1).description());
    }

    @Test
    void testCreate() {
        // Mock input
        DepartmentReqDto departmentReqDto = new DepartmentReqDto("Dept3", "Description3");

        // Test
        Department createdDepartment = new Department(3L, departmentReqDto.deptName(), departmentReqDto.description(), null);

        doNothing().when(departmentRepository).persist(any(Department.class));
        Mockito.when(departmentRepository.isPersistent(ArgumentMatchers.any(Department.class))).thenReturn(true);
        DepartmentResDto createdDto = departmentService.create(departmentReqDto);

        // Verify
        assertEquals("Dept3", createdDto.deptName());
        assertEquals("Description3", createdDto.description());
    }

    @Test
    void testFindById() {
        // Mock data
        Department department = new Department(1L, "Dept1", "Description1", null);

        // Mock behavior
        when(departmentRepository.findByIdOptional(1L)).thenReturn(Optional.of(department));

        // Test
        Department foundDepartment = departmentService.findById(1L);

        // Verify
        assertEquals("Dept1", foundDepartment.getDeptName());
        assertEquals("Description1", foundDepartment.getDescription());
    }

    @Test
    void testFindByIdNotFound() {
        // Mock behavior
        when(departmentRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        // Test and Verify
        assertThrows(RuntimeException.class, () -> departmentService.findById(1L));
    }
}
