package org.belajar.serviceTest.ojk;

import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.entity.ojk.Department;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.user.Users;
import org.belajar.api.repository.ojk.OjkDepartmentUserRepository;
import org.belajar.api.services.ojk.Implementation.DepartmentServiceImpl;
import org.belajar.api.services.ojk.Implementation.OjkDepartmentUserServiceImpl;
import org.belajar.api.services.user.UsersService;
import org.belajar.api.services.user.implementation.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class OjkDepartmentUserServiceImplTest {


    @InjectMocks
    OjkDepartmentUserServiceImpl ojkDepartmentUserService;
    @Mock
    OjkDepartmentUserRepository ojkDepartmentUserRepository;
    @Mock
    DepartmentServiceImpl departmentService;
    @Mock
    private UsersServiceImpl usersService;

    @Spy
    private Ojk ojk;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessOjkDepartmentUsers() {
        // Initialize ojkDepartmentUser list
        ojk.setOjkDepartmentUser(new ArrayList<>());

        // Mock data
        Department department = new Department();
        department.setDeptId(1L);
        Users user = new Users();
        user.setUserId(1L);
        OjkDepartmentUser ojkDepartmentUser = new OjkDepartmentUser();
        ojkDepartmentUser.setDepartment(department);
        ojkDepartmentUser.setUsers(user);

        List<OjkDepartmentUser> ojkDepartmentUsers = new ArrayList<>();
        ojkDepartmentUsers.add(ojkDepartmentUser);

        // Mock behavior
        when(departmentService.findById(1L)).thenReturn(department);
        when(usersService.findById(1L)).thenReturn(user);

        // Test the method
        ojkDepartmentUserService.processOjkDepartmentUsers(ojkDepartmentUsers, ojk);

        // Verify the method calls
        verify(departmentService, times(1)).findById(1L);
        verify(usersService, times(1)).findById(1L);
        verify(ojkDepartmentUserRepository, times(1)).persist(any(OjkDepartmentUser.class));
    }

    @Test
    public void testEditOjkDepartmentUser() {
        // Initialize ojkDepartmentUser list
        List<OjkDepartmentUser> ojkDepartmentUsers = new ArrayList<>();
        when(ojk.getOjkDepartmentUser()).thenReturn(ojkDepartmentUsers);

        // Mock data
        Department department = new Department();
        department.setDeptId(1L);
        Users user = new Users();
        user.setUserId(1L);

        OjkDepartmentUser existingOjkDepartmentUser = new OjkDepartmentUser();
        existingOjkDepartmentUser.setOduId(1L); // Set existing ID to simulate existing entity
        existingOjkDepartmentUser.setDepartment(department);
        existingOjkDepartmentUser.setUsers(user);

        OjkDepartmentUser newOjkDepartmentUser = new OjkDepartmentUser();
        newOjkDepartmentUser.setDepartment(department);
        newOjkDepartmentUser.setUsers(user);

        List<OjkDepartmentUser> updatedOjkDepartmentUsers = new ArrayList<>();
        updatedOjkDepartmentUsers.add(existingOjkDepartmentUser);
        updatedOjkDepartmentUsers.add(newOjkDepartmentUser);

        // Mock behavior
        when(departmentService.findById(1L)).thenReturn(department);
        when(usersService.findById(1L)).thenReturn(user);

        // Test the method
        List<OjkDepartmentUser> editedOjkDepartmentUsers = ojkDepartmentUserService.editOjkDepartmentUser(updatedOjkDepartmentUsers, ojk);

        // Verify the method calls
        verify(departmentService, times(2)).findById(1L); // Called for existing and new OjkDepartmentUser
        verify(usersService, times(2)).findById(1L); // Called for existing and new OjkDepartmentUser
        verify(ojkDepartmentUserRepository, times(2)).persist(any(OjkDepartmentUser.class)); // Called for existing and new OjkDepartmentUser
        // Assert other conditions if needed
    }

}