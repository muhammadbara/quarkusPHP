package org.belajar.api.dto.eventDto.history;

import org.belajar.api.entity.event.Event;
import org.belajar.api.enumeration.EventEnum;

import java.time.LocalDateTime;

public record HistoryResDto( Long historyId, EventEnum.action action, LocalDateTime actionTime,Event event) {
}
