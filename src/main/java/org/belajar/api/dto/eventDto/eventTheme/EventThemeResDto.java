package org.belajar.api.dto.eventDto.eventTheme;

import org.belajar.api.entity.event.Event;

import java.util.List;

public record EventThemeResDto(Long etId, String name, String description, List<Event> events) {
}
