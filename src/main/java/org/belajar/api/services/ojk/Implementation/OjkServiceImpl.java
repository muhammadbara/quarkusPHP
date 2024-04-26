package org.belajar.api.services.ojk.Implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.ojkDto.ojk.OjkReqDto;
import org.belajar.api.dto.ojkDto.ojk.OjkResDto;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.ojk.OjkFile;
import org.belajar.api.enumeration.OjkEnum;
import org.belajar.api.repository.ojk.OjkRepository;
import org.belajar.api.services.ojk.OjkDepartmentUserService;
import org.belajar.api.services.ojk.OjkFileService;
import org.belajar.api.services.ojk.OjkService;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OjkServiceImpl implements OjkService {

    @Inject
    OjkRepository ojkRepository;
    @Inject
    OjkFileService ojkFileService;
    @Inject
    OjkDepartmentUserService ojkDepartmentUserService;


    @Override
    public List<OjkResDto>getAll(){

        return ojkRepository.findAll().stream().map(ojk -> new OjkResDto(ojk.getOjkId(),ojk.getTitle(),ojk.getStatus(),ojk.getEmailBody(),ojk.getStartDate(),ojk.getEndDate(),ojk.getReminderType(),ojk.getAttachment(),ojk.getPriority(),ojk.getOjkDepartmentUser())).toList();
    }

    public static LocalDate formatDateString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    @Override
    public Ojk findById(Long ojkId){
       return ojkRepository.findByIdOptional(ojkId).orElseThrow(() -> new RuntimeException("ojk not found"));
    }

    @Override
    @Transactional
    public OjkResDto create(OjkReqDto ojkReqDto, FileUpload file) throws Exception {
        try {
            LocalDate startDate = formatDateString(ojkReqDto.startDate());
            LocalDate endDate = formatDateString(ojkReqDto.endDate());

            OjkFile ojkFile = ojkFileService.sendUpload(file);

            Ojk ojk = Ojk.builder()
                    .title(ojkReqDto.title())
                    .emailBody(ojkReqDto.emailBody())
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(ojkReqDto.status())
                    .reminderType(ojkReqDto.reminderType())
                    .ojkFile(ojkFile)
                    .attachment(ojkFile != null ? ojkFile.getFileName() : null)
                    .ojkDepartmentUser(new ArrayList<>())
                    .priority(ojkReqDto.priority())
                    .modifyDate(LocalDateTime.now()).build();

            if (ojkFile != null) {
                ojkFile.setOjk(ojk);
            }

            ojkDepartmentUserService.processOjkDepartmentUsers(ojkReqDto.ojkDepartmentUser(), ojk);
            ojkRepository.persist(ojk);

            return new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser());
        } catch (Exception e) {
            throw new Exception("Terjadi kesalahan saat membuat entitas Ojk: " + e.getMessage());
        }
    }

//masih bug edit

    @Override
    @Transactional
    public OjkResDto edit(Long ojkId, OjkReqDto ojkReqDto, FileUpload file) throws Exception {
        Ojk ojk = findById(ojkId);
        LocalDate startDate=formatDateString(ojkReqDto.startDate());
        LocalDate endDate=formatDateString(ojkReqDto.endDate());
        OjkFile ojkFile = ojkFileService.update(ojkId, file);

        ojk.setTitle(ojkReqDto.title());
        ojk.setEmailBody(ojkReqDto.emailBody());
        ojk.setStartDate(startDate);
        ojk.setEndDate(endDate);
        ojk.setStatus(ojkReqDto.status());
        ojk.setReminderType(ojkReqDto.reminderType());
        ojk.setPriority(ojkReqDto.priority());
        ojk.setModifyDate(LocalDateTime.now());
        ojk.setOjkFile(ojkFile);
        ojk.setAttachment(ojkFile.getFileName());

        List<OjkDepartmentUser> ojkDepartmentUsers = ojkDepartmentUserService.editOjkDepartmentUser(ojkReqDto.ojkDepartmentUser(), ojk);
        ojk.setOjkDepartmentUser(ojkDepartmentUsers);

        ojkRepository.persist(ojk);

        return new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser());
    }

    @Override
    @Transactional
    public void deleteById(Long ojkId){
        Ojk ojk = findById(ojkId);
        ojkRepository.delete(ojk);
    }

    @Override
    public List<OjkResDto> findByTitle(String title){
      List<Ojk> byTittle = ojkRepository.findByTittle(title).orElseThrow(()->new RuntimeException("title"+title+"not found"));
      return byTittle.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto> findByStatus(OjkEnum.status status){
        List<Ojk> byStatus = ojkRepository.findByStatus(status).orElseThrow(()->new RuntimeException("status "+status+" not found"));
        return byStatus.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto> findByAttachment(String attachment){
        List<Ojk> byAttachment = ojkRepository.findByAttachment(attachment).orElseThrow(()->new RuntimeException("attachment "+attachment+" not found"));
        return byAttachment.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto> findByStartDate(String startDate){
        LocalDate date = formatDateString(startDate);
        List<Ojk> byStartDate = ojkRepository.findByStartDate(date).orElseThrow(()->new RuntimeException("start date "+startDate+" not found"));
        return byStartDate.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto> findByEndDate(String endDate){
        LocalDate date = formatDateString(endDate);
        List<Ojk> byStartDate = ojkRepository.findByEndDate(date).orElseThrow(()->new RuntimeException("end date "+endDate+" not found"));
        return byStartDate.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto>findByReminderType(OjkEnum.reminderType reminderType){
        List<Ojk> byReminderType = ojkRepository.findByReminderType(reminderType).orElseThrow(()->new RuntimeException("reminder type "+reminderType+" not found"));
        return byReminderType.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }

    @Override
    public List<OjkResDto>findByPriority(OjkEnum.priority priority){
        List<Ojk> byPriority = ojkRepository.findByPriority(priority).orElseThrow(()->new RuntimeException("priority "+priority+" not found"));
        return byPriority.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(),ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();
    }



}
