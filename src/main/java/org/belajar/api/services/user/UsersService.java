package org.belajar.api.services.user;

import org.belajar.api.dto.usersDto.UsersReqDto;
import org.belajar.api.dto.usersDto.UsersResDto;
import org.belajar.api.entity.user.Users;

import java.util.List;

public interface UsersService {
    List<UsersResDto> getAll();
    UsersResDto create(UsersReqDto usersReqDto);
    Users findById(Long id);
}
