package org.belajar.api.services.event;

import org.belajar.api.dto.eventDto.history.HistoryResDto;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.event.History;
import org.belajar.api.enumeration.EventEnum;

import java.util.List;

public interface HistoryService {
    History saveHistory(EventEnum.action action, Event event);
    List<HistoryResDto> getAll();
}
