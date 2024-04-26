package org.belajar.api.services.event.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.eventDto.regist.RegistReqDto;
import org.belajar.api.dto.eventDto.regist.RegistResDto;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.user.Users;
import org.belajar.api.repository.event.EventRepository;
import org.belajar.api.repository.user.UsersRepository;
import org.belajar.api.services.event.EventService;
import org.belajar.api.services.event.RegistService;
import org.belajar.api.services.user.UsersService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RegistServiceImpl implements RegistService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventService eventService;

    @Inject
    UsersService usersService;

    @Inject
    UsersRepository usersRepository;

    @Override
    @Transactional
    public RegistResDto registEvent(Long id, RegistReqDto registEventDto) {
        Users users = usersService.findById(id);
        Event event = eventService.findByid(registEventDto.eventId());

        if (event.getEndDate().isBefore(LocalDate.now()) || event.getEndDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("the event has been ended");
        }

        List<Users> usersList = event.getUsers();
        if (usersList.isEmpty()) {
           usersList = new ArrayList<>();
        }

        usersList.add(users);
        event.setUsers(usersList);
        eventRepository.persist(event);
        usersRepository.persist(users);

        return new RegistResDto(event.getEventId(),event.getTittle(),users.getUserId(),users.getName());
    }

    @Override
    @Transactional
    public void deleteRegist(Long eventId, Long usersId){
        eventRepository.deleteByUserIdAndEventId(eventId,usersId);
    }
}
