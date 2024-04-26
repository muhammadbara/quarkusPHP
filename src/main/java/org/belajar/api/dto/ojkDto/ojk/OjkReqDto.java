package org.belajar.api.dto.ojkDto.ojk;

import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.user.Users;
import org.belajar.api.enumeration.OjkEnum;

import java.time.LocalDate;
import java.util.List;

public record OjkReqDto(String title, OjkEnum.status status, String emailBody, String startDate, String endDate, OjkEnum.reminderType reminderType, OjkEnum.priority priority, List<OjkDepartmentUser>ojkDepartmentUser) {
}
