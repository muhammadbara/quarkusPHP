package org.belajar.api.dto.eventDto.category;

import org.belajar.api.entity.event.Event;

import java.util.List;

public record CategoryResDto(Long catId, String categoryName, String description, List<Event> events) {
}
