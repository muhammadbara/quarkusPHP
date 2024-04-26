package org.belajar.api.services.user.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.usersDto.UsersReqDto;
import org.belajar.api.dto.usersDto.UsersResDto;
import org.belajar.api.entity.user.Users;
import org.belajar.api.repository.user.UsersRepository;
import org.belajar.api.services.event.EventService;
import org.belajar.api.services.user.UsersService;

import java.util.List;

@ApplicationScoped
public class UsersServiceImpl implements UsersService {

    @Inject
    UsersRepository usersRepository;

    @Inject
    EventService eventService;

    @Override
    public List<UsersResDto> getAll(){
       return usersRepository.findAll().stream().map(users -> new UsersResDto(users.getUserId(),users.getName(),users.getPosition())).toList();
    }

    @Override
    @Transactional
    public UsersResDto create(UsersReqDto usersReqDto){
        Users users = new Users();
        users.setName(usersReqDto.name());
        users.setPosition(usersReqDto.position());
        usersRepository.persist(users);

        return new UsersResDto(users.getUserId(),users.getName(),users.getPosition());
    }

    @Override
    public Users findById(Long id){
        return usersRepository.findByIdOptional(id).orElseThrow(()->new RuntimeException("Id not available"));
    }



}
