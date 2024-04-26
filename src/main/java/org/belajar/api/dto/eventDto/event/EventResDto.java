package org.belajar.api.dto.eventDto.event;

import io.quarkus.panache.common.Page;
import org.belajar.api.entity.event.Category;
import org.belajar.api.entity.event.EventTheme;
import org.belajar.api.entity.user.Users;
import org.belajar.api.enumeration.EventEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record EventResDto(Long eventId, String tittle, String description, LocalDate startDate, LocalDate endDate, EventEnum.needVolunteer needVolunteer, EventEnum.audience audience, EventEnum.status status, Category category, EventTheme eventTheme, String imagePath, LocalDateTime modifyDate, List<Users> users) {
}


