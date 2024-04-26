package org.belajar.api.services.event.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeReqDto;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeResDto;
import org.belajar.api.entity.event.EventTheme;
import org.belajar.api.repository.event.EventThemeRepository;
import org.belajar.api.services.event.EventThemeService;

import java.util.List;

@ApplicationScoped
public class EventThemeServiceImpl implements EventThemeService {
    @Inject
    EventThemeRepository eventThemeRepository;

    @Override
    public List<EventThemeResDto> getAll(){
       return eventThemeRepository.findAll().stream().map(eventTheme -> new EventThemeResDto(eventTheme.getEtId(),eventTheme.getName(),eventTheme.getDescription(),eventTheme.getEvents())).toList();
    }

    @Override
    @Transactional
    public EventThemeResDto create(EventThemeReqDto eventThemeReqDto){
        EventTheme eventTheme = new EventTheme();
        eventTheme.setName(eventThemeReqDto.name());
        eventTheme.setDescription(eventThemeReqDto.description());
        eventThemeRepository.persist(eventTheme);

        return new EventThemeResDto(eventTheme.getEtId(),eventTheme.getName(),eventTheme.getDescription(),eventTheme.getEvents());
    }

    @Override
    public EventTheme findById(Long id){
        return eventThemeRepository.findByIdOptional(id).orElseThrow(()-> new RuntimeException("id not available"));
    }
    @Override
    @Transactional
    public EventThemeResDto edit(EventThemeReqDto eventThemeReqDto, Long id){
        EventTheme eventTheme = findById(id);
        eventTheme.setName(eventThemeReqDto.name());
        eventTheme.setDescription(eventThemeReqDto.description());
        eventThemeRepository.persist(eventTheme);

        return new EventThemeResDto(eventTheme.getEtId(),eventTheme.getName(),eventTheme.getDescription(),eventTheme.getEvents());
    }

    @Override
    public void delete(Long id){
        eventThemeRepository.deleteById(id);
    }
}
