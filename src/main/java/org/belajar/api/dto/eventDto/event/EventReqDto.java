package org.belajar.api.dto.eventDto.event;

import org.belajar.api.enumeration.EventEnum;


public record EventReqDto(String tittle, String description, String startDate, String endDate, EventEnum.needVolunteer needVolunteer, EventEnum.audience audience, EventEnum.status status, Long catId, Long etId) {
}
