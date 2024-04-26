package org.belajar.api.dto.ojkDto.ojk;

import org.belajar.api.entity.ojk.Department;
import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.user.Users;
import org.belajar.api.enumeration.OjkEnum;

import java.time.LocalDate;
import java.util.List;

public record OjkResDto(Long ojkId, String title, OjkEnum.status status, String emailBody, LocalDate startDate, LocalDate endDate, OjkEnum.reminderType reminderType, String attachment, OjkEnum.priority priority, List<OjkDepartmentUser>ojkDepartmentUser) {
}
