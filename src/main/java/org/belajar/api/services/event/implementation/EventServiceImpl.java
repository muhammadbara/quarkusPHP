package org.belajar.api.services.event.implementation;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.eventDto.event.EventReqDto;
import org.belajar.api.dto.eventDto.event.EventResDto;
import org.belajar.api.entity.event.Category;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.event.EventImage;
import org.belajar.api.entity.event.EventTheme;

import org.belajar.api.enumeration.EventEnum;
import org.belajar.api.repository.event.EventRepository;
import org.belajar.api.services.event.*;
import org.jboss.resteasy.reactive.multipart.FileUpload;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventServiceImpl implements EventService {

    @Inject
    EventRepository eventRepository;
    @Inject
    EventImageService eventImageService;
    @Inject
    CategoryService categoryService;
    @Inject
    HistoryService historyService;
    @Inject
    EventThemeService eventThemeService;


    @Override
    public List<EventResDto> getAll(int page, int size) {
        return eventRepository.findAll().page(page, size).list().stream()
                .map(event -> new EventResDto(
                        event.getEventId(),
                        event.getTittle(),
                        event.getDescription(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getNeedVolunteer(),
                        event.getAudience(),
                        event.getStatus(),
                        event.getCategory(),
                        event.getEventTheme(),
                        event.getImagePath(),
                        event.getModifyDate(),
                        event.getUsers()))
                .collect(Collectors.toList());
    }





    @Override
    @Transactional
    public EventResDto create(EventReqDto eventReqDto, FileUpload file) throws IOException {
        try {
            EventTheme eventTheme = eventThemeService.findById(eventReqDto.etId());
            Category category = categoryService.findById(eventReqDto.catId());
            LocalDate startDate = formatDateString(eventReqDto.startDate());
            LocalDate endDate = formatDateString(eventReqDto.endDate());
            EventImage eventImage = eventImageService.sendUpload(file);

            Event event = Event.builder()
                    .tittle(eventReqDto.tittle())
                    .description(eventReqDto.description())
                    .startDate(startDate)
                    .endDate(endDate)
                    .needVolunteer(eventReqDto.needVolunteer())
                    .audience(eventReqDto.audience())
                    .status(eventReqDto.status())
                    .category(category)
                    .eventImages(eventImage)
                    .imagePath(eventImage.getImageName())
                    .eventTheme(eventTheme)
                    .modifyDate(LocalDateTime.now())
                    .build();

            eventImage.setEvent(event);

            eventRepository.persist(event);

            historyService.saveHistory(EventEnum.action.CREATE, event);

            return new EventResDto(event.getEventId(), event.getTittle(), event.getDescription(), event.getStartDate(), event.getEndDate(), event.getNeedVolunteer(), event.getAudience(), event.getStatus(), event.getCategory(), event.getEventTheme(), event.getImagePath(), event.getModifyDate(), event.getUsers());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Gagal membuat event: " + e.getMessage());
        }
    }


    @Override
    public Event findByid(Long id){
        return eventRepository.findByIdOptional(id).orElseThrow(()-> new RuntimeException("id not available"));
    }

    @Override
    @Transactional
    public EventResDto edit(EventReqDto eventReqDto, Long id, FileUpload file) throws IOException {
        Event event = findByid(id);
        EventTheme eventTheme = eventThemeService.findById(eventReqDto.etId());
        Category category=categoryService.findById(eventReqDto.catId());
        LocalDate startDate=formatDateString(eventReqDto.startDate());
        LocalDate endDate=formatDateString(eventReqDto.endDate());

        EventImage eventImage = eventImageService.update(id, file);

        event.setTittle(eventReqDto.tittle());
        event.setDescription(eventReqDto.description());
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setNeedVolunteer(eventReqDto.needVolunteer());
        event.setAudience(eventReqDto.audience());
        event.setStatus(eventReqDto.status());
        event.setCategory(category);
        event.setEventTheme(eventTheme);
        event.setEventImages(eventImage);
        event.setImagePath(eventImage.getImageName());

        eventRepository.persist(event);

        historyService.saveHistory(EventEnum.action.UPDATE,event);

        return new EventResDto(event.getEventId(),event.getTittle(),event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(), event.getImagePath(),event.getModifyDate(),event.getUsers());
    }

    @Override
    @Transactional
    public void delete(Long id){
        Event event = findByid(id);
        historyService.saveHistory(EventEnum.action.DELETE,event);
        eventRepository.delete(event);
    }

    @Override
    public List<EventResDto> findByTitle(String tittle){
        List<Event> tittleEvent = eventRepository.findByTittle(tittle).orElseThrow(() -> new RuntimeException("tittle not found"));
        return tittleEvent.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto> findByCat(Long catId){
        List<Event> categoryEvents = eventRepository.findByCat(catId).orElseThrow(() -> new RuntimeException("category not found"));
        return categoryEvents.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto> findByEventTheme(Long etId){
        List<Event> eventThemeEvents = eventRepository.findByEventTheme(etId).orElseThrow(() -> new RuntimeException("event theme not found"));
        return eventThemeEvents.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto> findByNeedVolunteer(EventEnum.needVolunteer needVolunteer){
        List<Event> needVolunteerEvent = eventRepository.findByVolunteer(needVolunteer).orElseThrow(() -> new RuntimeException("need volunteer not found"));
        return needVolunteerEvent.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto> findByAudience(EventEnum.audience audience){
        List<Event> audienceEvents = eventRepository.findByAudience(audience).orElseThrow(() -> new RuntimeException("audience not found"));
        return audienceEvents.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto>findByStatus(EventEnum.status status){
        List<Event> statusEvent = eventRepository.findByStatus(status).orElseThrow(() -> new RuntimeException("status noy found"));
        return statusEvent.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    public static LocalDate formatDateString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    @Override
    public List<EventResDto>findByStartDate(String startDate){
        LocalDate date = formatDateString(startDate);
        List<Event> findByStartDate=eventRepository.findByStartDate(date).orElseThrow(()->new RuntimeException("startDate"+startDate+"Not Found"));
        return findByStartDate.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }

    @Override
    public List<EventResDto>findByEndDate(String endDate){
        LocalDate date = formatDateString(endDate);
        List<Event> findByEndDate=eventRepository.findByEndDate(date).orElseThrow(()->new RuntimeException("endDate"+endDate+"Not Found"));
        return findByEndDate.stream().map(event -> new EventResDto(event.getEventId(),event.getTittle(),
                event.getDescription(),event.getStartDate(),event.getEndDate(),event.getNeedVolunteer(),
                event.getAudience(),event.getStatus(),event.getCategory(),event.getEventTheme(),event.getImagePath(),
                event.getModifyDate(),event.getUsers())).toList();
    }






}
