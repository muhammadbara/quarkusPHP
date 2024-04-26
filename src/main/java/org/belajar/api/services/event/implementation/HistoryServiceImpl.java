package org.belajar.api.services.event.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.belajar.api.dto.eventDto.history.HistoryResDto;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.event.History;
import org.belajar.api.enumeration.EventEnum;
import org.belajar.api.repository.event.HistoryRepository;
import org.belajar.api.services.event.HistoryService;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class HistoryServiceImpl implements HistoryService {
    @Inject
    HistoryRepository historyRepository;

    @Override
    public History saveHistory(EventEnum.action action, Event event){
        History history = new History();
        history.setAction(action);
        history.setEvent(event);
        history.setActionTime(LocalDateTime.now());

        historyRepository.persist(history);

        return history;
    }

    @Override
    public List<HistoryResDto> getAll(){
        return historyRepository.findAll().stream().map(history -> new HistoryResDto(history.getHistoryId(),history.getAction(),history.getActionTime(),history.getEvent())).toList();
    }
}
