package org.belajar.api.services.event;

import org.belajar.api.dto.eventDto.eventTheme.EventThemeReqDto;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeResDto;
import org.belajar.api.entity.event.EventTheme;

import java.util.List;

public interface EventThemeService {

    List<EventThemeResDto>  getAll();
    EventThemeResDto create(EventThemeReqDto eventThemeReqDto);
    EventTheme findById(Long id);
    EventThemeResDto edit(EventThemeReqDto eventThemeReqDto, Long id);
    void delete(Long id);
}
