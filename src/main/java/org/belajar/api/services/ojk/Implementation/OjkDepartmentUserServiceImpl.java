package org.belajar.api.services.ojk.Implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.ojkDto.ojkDeprtmentUser.OjkDepartmentUserDto;
import org.belajar.api.entity.ojk.Department;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.user.Users;
import org.belajar.api.repository.ojk.OjkDepartmentUserRepository;
import org.belajar.api.services.ojk.DepartmentService;
import org.belajar.api.services.ojk.OjkDepartmentUserService;
import org.belajar.api.services.user.UsersService;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OjkDepartmentUserServiceImpl implements OjkDepartmentUserService {
    @Inject
    OjkDepartmentUserRepository ojkDepartmentUserRepository;
    @Inject
    DepartmentService departmentService;
    @Inject
    UsersService usersService;


    @Override
    @Transactional
    public void processOjkDepartmentUsers(List<OjkDepartmentUser> ojkDepartmentUsers, Ojk ojk) {

        for (OjkDepartmentUser ojkDepartmentUser : ojkDepartmentUsers) {
            Department department = departmentService.findById(ojkDepartmentUser.getDepartment().getDeptId());
            Users user = usersService.findById(ojkDepartmentUser.getUsers().getUserId());

            OjkDepartmentUser departmentUserOjk = new OjkDepartmentUser();
            departmentUserOjk.setDepartment(department);
            departmentUserOjk.setUsers(user);
            departmentUserOjk.setOjk(ojk);

             ojk.getOjkDepartmentUser().add(departmentUserOjk);
            ojkDepartmentUserRepository.persist(departmentUserOjk);
        }
    }

    @Transactional
    @Override
    public List<OjkDepartmentUser> editOjkDepartmentUser(List<OjkDepartmentUser> updatedOjkDepartmentUsers, Ojk ojk) {
        // Hapus semua entitas OjkDepartmentUser yang terkait dengan Ojk

        List<OjkDepartmentUser> editedOjkDepartmentUsers = new ArrayList<>();

        for (OjkDepartmentUser updatedOjkDepartmentUser : updatedOjkDepartmentUsers) {
            Department department = departmentService.findById(updatedOjkDepartmentUser.getDepartment().getDeptId());
            Users user = usersService.findById(updatedOjkDepartmentUser.getUsers().getUserId());

            if (updatedOjkDepartmentUser.getOduId() != null) {
                // Jika OjkDepartmentUser sudah ada (tidak baru), gunakan merge
                updatedOjkDepartmentUser.setDepartment(department);
                updatedOjkDepartmentUser.setUsers(user);
                updatedOjkDepartmentUser.setOjk(ojk);
                ojk.getOjkDepartmentUser().add(updatedOjkDepartmentUser);
                ojkDepartmentUserRepository.persist(updatedOjkDepartmentUser);
            } else {
                // Jika OjkDepartmentUser baru, buat instansi baru dan gunakan persist
                updatedOjkDepartmentUser=new OjkDepartmentUser();
                updatedOjkDepartmentUser.setDepartment(department);
                updatedOjkDepartmentUser.setUsers(user);
                updatedOjkDepartmentUser.setOjk(ojk);

                ojk.getOjkDepartmentUser().add(updatedOjkDepartmentUser);
                ojkDepartmentUserRepository.persist(updatedOjkDepartmentUser);
            }

            editedOjkDepartmentUsers.add(updatedOjkDepartmentUser);
        }

        return editedOjkDepartmentUsers;
    }







}
