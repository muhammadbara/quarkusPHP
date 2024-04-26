package org.belajar.api.services.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.belajar.api.dto.eventDto.event.EventReqDto;
import org.belajar.api.dto.eventDto.event.EventResDto;
import org.belajar.api.entity.event.Event;
import org.belajar.api.enumeration.EventEnum;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.List;

public interface EventService {
    List<EventResDto> getAll(int page, int size);
    EventResDto create(EventReqDto eventReqDto, FileUpload file) throws IOException;
    Event findByid(Long id);
    EventResDto edit(EventReqDto eventReqDto, Long id, FileUpload file) throws IOException;
    void delete(Long id);
    List<EventResDto> findByTitle(String title);
    List<EventResDto> findByCat(Long catId);
    List<EventResDto> findByEventTheme(Long etId);
    List<EventResDto> findByNeedVolunteer(EventEnum.needVolunteer needVolunteer);
    List<EventResDto> findByAudience(EventEnum.audience audience);
    List<EventResDto>findByStatus(EventEnum.status status);
    List<EventResDto>findByStartDate(String startDate);
    List<EventResDto>findByEndDate(String endDate);

}
