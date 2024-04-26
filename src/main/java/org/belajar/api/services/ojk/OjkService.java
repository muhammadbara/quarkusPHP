package org.belajar.api.services.ojk;

import org.belajar.api.dto.ojkDto.ojk.OjkReqDto;
import org.belajar.api.dto.ojkDto.ojk.OjkResDto;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.enumeration.OjkEnum;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

public interface OjkService {
    List<OjkResDto> getAll();
    OjkResDto create(OjkReqDto ojkReqDto, FileUpload file) throws Exception;
    void deleteById(Long ojkId);
    Ojk findById(Long ojkId);
    OjkResDto edit(Long ojkId, OjkReqDto ojkReqDto, FileUpload file) throws Exception;
    List<OjkResDto> findByTitle(String title);
    List<OjkResDto> findByStatus(OjkEnum.status status);
    List<OjkResDto> findByAttachment(String attachment);
    List<OjkResDto> findByStartDate(String startDate);
    List<OjkResDto> findByEndDate(String endDate);
    List<OjkResDto>findByReminderType(OjkEnum.reminderType reminderType);
    List<OjkResDto>findByPriority(OjkEnum.priority priority);
}
