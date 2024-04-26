package org.belajar.api.dto.eventDto.event;

import java.util.List;

public record EventPageDto(int page,
                           int size,
                           int totalElements,
                           List<EventResDto> events) {
}
