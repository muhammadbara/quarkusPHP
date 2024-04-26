package org.belajar.api.services.event;

import org.belajar.api.dto.eventDto.regist.RegistReqDto;
import org.belajar.api.dto.eventDto.regist.RegistResDto;

public interface RegistService {

    RegistResDto registEvent(Long id, RegistReqDto registEventDto) ;
    void deleteRegist(Long eventId, Long usersId);
}
