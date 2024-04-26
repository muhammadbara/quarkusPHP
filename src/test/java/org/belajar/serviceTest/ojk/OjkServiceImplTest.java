package org.belajar.serviceTest.ojk;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.dto.ojkDto.department.DepartmentResDto;
import org.belajar.api.dto.ojkDto.ojk.OjkReqDto;
import org.belajar.api.dto.ojkDto.ojk.OjkResDto;
import org.belajar.api.entity.ojk.Department;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.entity.ojk.OjkDepartmentUser;
import org.belajar.api.entity.ojk.OjkFile;
import org.belajar.api.enumeration.OjkEnum;
import org.belajar.api.repository.ojk.OjkRepository;
import org.belajar.api.services.ojk.Implementation.OjkServiceImpl;
import org.belajar.api.services.ojk.OjkDepartmentUserService;
import org.belajar.api.services.ojk.OjkFileService;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@QuarkusTest
class OjkServiceImplTest {

    @InjectMocks
    OjkServiceImpl ojkService;
    @Mock
    OjkRepository ojkRepository;
    @Mock
    OjkFileService ojkFileService;
    @Mock
    OjkDepartmentUserService ojkDepartmentUserService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Ojk ojk(){
        Ojk ojk = new Ojk();
        ojk.setOjkId(1L);
        ojk.setTitle("Contoh OJK");
        ojk.setStatus(OjkEnum.status.ACTIVE);
        ojk.setEmailBody("Ini adalah email body contoh.");
        ojk.setStartDate(LocalDate.now());
        ojk.setEndDate(LocalDate.now().plusDays(7));
        ojk.setReminderType(OjkEnum.reminderType.DAILY);
        ojk.setPriority(OjkEnum.priority.HIGH);
        ojk.setAttachment("file/path/contoh.pdf");
        ojk.setModifyDate(LocalDateTime.now());

        List<OjkDepartmentUser> ojkDepartmentUsers = new ArrayList<>();
        OjkDepartmentUser departmentUser1 = new OjkDepartmentUser();
        ojkDepartmentUsers.add(departmentUser1);
        OjkDepartmentUser departmentUser2 = new OjkDepartmentUser();
        ojkDepartmentUsers.add(departmentUser2);

        ojk.setOjkDepartmentUser(ojkDepartmentUsers);
        OjkFile ojkFile = new OjkFile();
        ojk.setOjkFile(ojkFile);

        return ojk;
    }

    Ojk ojk2(){
        Ojk ojk = new Ojk();
        ojk.setOjkId(2L);
        ojk.setTitle("Contoh OJK2");
        ojk.setStatus(OjkEnum.status.ACTIVE);
        ojk.setEmailBody("Ini adalah email body contoh.");
        ojk.setStartDate(LocalDate.now());
        ojk.setEndDate(LocalDate.now().plusDays(7));
        ojk.setReminderType(OjkEnum.reminderType.DAILY);
        ojk.setPriority(OjkEnum.priority.HIGH);
        ojk.setAttachment("file/path/contoh.pdf");
        ojk.setModifyDate(LocalDateTime.now());

        List<OjkDepartmentUser> ojkDepartmentUsers = new ArrayList<>();
        OjkDepartmentUser departmentUser1 = new OjkDepartmentUser();
        ojkDepartmentUsers.add(departmentUser1);
        OjkDepartmentUser departmentUser2 = new OjkDepartmentUser();
        ojkDepartmentUsers.add(departmentUser2);

        ojk.setOjkDepartmentUser(ojkDepartmentUsers);
        OjkFile ojkFile = new OjkFile();
        ojk.setOjkFile(ojkFile);

        return ojk;
    }

    @Test
    void testFindById() {
        // Mock data
        Ojk ojk = ojk();

        // Mock behavior
        when(ojkRepository.findByIdOptional(1L)).thenReturn(Optional.of(ojk));

        // Test
        Ojk foundOjk = ojkService.findById(1L);

        // Verify
        assertEquals("Contoh OJK", foundOjk.getTitle());
        assertEquals(OjkEnum.status.ACTIVE, foundOjk.getStatus());
        assertEquals("Ini adalah email body contoh.", foundOjk.getEmailBody());
        assertEquals(LocalDate.now(), foundOjk.getStartDate());
        assertEquals(LocalDate.now().plusDays(7), foundOjk.getEndDate());
        assertEquals(OjkEnum.reminderType.DAILY, foundOjk.getReminderType());
        assertEquals(OjkEnum.priority.HIGH, foundOjk.getPriority());
        assertEquals("file/path/contoh.pdf", foundOjk.getAttachment());
        assertEquals(ojk.getModifyDate(), foundOjk.getModifyDate());

        // Verify OjkDepartmentUser
        assertEquals(2, foundOjk.getOjkDepartmentUser().size());

        // Verify OjkFile
        assertNotNull(foundOjk.getOjkFile());
    }

    @Test
    void testFindByIdNotFound() {
        // Mock behavior
        when(ojkRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        // Test and Verify
        assertThrows(RuntimeException.class, () -> ojkService.findById(1L));
    }

    @Test
    void testGetAll() {
        Ojk ojk1 = ojk();
        Ojk ojk2 = ojk2();
        List<Ojk> ojkList = List.of(ojk1, ojk2);

        List<OjkResDto> ojkResDtoList = ojkList.stream().map(ojk -> new OjkResDto(ojk.getOjkId(), ojk.getTitle(), ojk.getStatus(), ojk.getEmailBody(), ojk.getStartDate(), ojk.getEndDate(), ojk.getReminderType(), ojk.getAttachment(), ojk.getPriority(), ojk.getOjkDepartmentUser())).toList();

        PanacheQuery<Ojk> panacheQuery = mock(PanacheQuery.class);

        when(panacheQuery.stream()).thenReturn(ojkList.stream());
        when(ojkRepository.findAll()).thenReturn(panacheQuery);

        // Test
        List<OjkResDto> result = ojkService.getAll();
        assertEquals(ojkResDtoList.size(), result.size());
        for (int i = 0; i < ojkResDtoList.size(); i++) {
            OjkResDto expected = ojkResDtoList.get(i);
            OjkResDto actual = result.get(i);
            assertEquals(expected.ojkId(), actual.ojkId());
            assertEquals(expected.title(), actual.title());
            assertEquals(expected.status(), actual.status());
            assertEquals(expected.emailBody(), actual.emailBody());
            assertEquals(expected.startDate(), actual.startDate());
            assertEquals(expected.endDate(), actual.endDate());
            assertEquals(expected.reminderType(), actual.reminderType());
            assertEquals(expected.attachment(), actual.attachment());
            assertEquals(expected.priority(), actual.priority());
            // Add more assertions if needed

        }
    }

    OjkReqDto ojkReqDto(){
        OjkReqDto ojkReqDto = new OjkReqDto("Contoh OJK",OjkEnum.status.ACTIVE,"Ini adalah email body contoh.","2024-04-25","2024-05-05",OjkEnum.reminderType.DAILY,OjkEnum.priority.HIGH,new ArrayList<>());
        return ojkReqDto;
    }


    @Test
    void testCreate_Successful() throws Exception {
        // Mock data
        OjkReqDto ojkReqDto = ojkReqDto();

        FileUpload fileUpload = new FileUpload() {
            @Override
            public String name() {
                return "name";
            }

            @Override
            public Path filePath() {
                return null;
            }

            @Override
            public String fileName() {
                return "filename";
            }

            @Override
            public long size() {
                return 0;
            }

            @Override
            public String contentType() {
                return null;
            }

            @Override
            public String charSet() {
                return null;
            }
        };
        // Set fileUpload properties if needed

        OjkFile ojkFile = new OjkFile();
        // Set properties for ojkFile if needed

        // Mock behavior
        when(ojkFileService.sendUpload(any())).thenReturn(ojkFile);

        // Test
        OjkResDto result = ojkService.create(ojkReqDto, fileUpload);

        // Verify
        assertNotNull(result);
        assertEquals("Contoh OJK", result.title());
        assertEquals("Ini adalah email body contoh.", result.emailBody());
        assertEquals(LocalDate.parse("2024-04-25"), result.startDate());
        assertEquals(LocalDate.parse("2024-05-05"), result.endDate());
        assertEquals(OjkEnum.status.ACTIVE, result.status());
        assertEquals(OjkEnum.reminderType.DAILY, result.reminderType());
        assertEquals(OjkEnum.priority.HIGH, result.priority());
        assertEquals(ojkFile.getFileName(), result.attachment());
        // Add more assertions if needed
    }


    @Test
    void testCreate_ExceptionInPersistence() throws Exception {
        // Mock data
        OjkReqDto ojkReqDto = ojkReqDto();

        FileUpload fileUpload = new FileUpload() {
            @Override
            public String name() {
                return "name";
            }

            @Override
            public Path filePath() {
                return null;
            }

            @Override
            public String fileName() {
                return "filename";
            }

            @Override
            public long size() {
                return 0;
            }

            @Override
            public String contentType() {
                return null;
            }

            @Override
            public String charSet() {
                return null;
            }
        };
        // Set fileUpload properties if needed

        // Mock behavior
        when(ojkFileService.sendUpload(any())).thenReturn(null);
        doThrow(new RuntimeException("Failed to persist Ojk")).when(ojkRepository).persist(any(Ojk.class));

        // Test and Verify
        assertThrows(Exception.class, () -> ojkService.create(ojkReqDto, fileUpload));
    }


    @Test
    void testCreate_ExceptionInDepartmentUserProcessing() throws Exception {
        // Mock data
        OjkReqDto ojkReqDto = ojkReqDto();

        FileUpload fileUpload = new FileUpload() {
            @Override
            public String name() {
                return "name";
            }

            @Override
            public Path filePath() {
                return null;
            }

            @Override
            public String fileName() {
                return "filename";
            }

            @Override
            public long size() {
                return 0;
            }

            @Override
            public String contentType() {
                return null;
            }

            @Override
            public String charSet() {
                return null;
            }
        };
        // Set fileUpload properties if needed

        OjkFile ojkFile = new OjkFile();
        // Set properties for ojkFile if needed

        // Mock behavior
        when(ojkFileService.sendUpload(any())).thenReturn(ojkFile);
        doThrow(new RuntimeException("Failed to process OjkDepartmentUsers")).when(ojkDepartmentUserService).processOjkDepartmentUsers(anyList(), any());

        // Test and Verify
        assertThrows(Exception.class, () -> ojkService.create(ojkReqDto, fileUpload));
    }

    @Test
    void testEdit() throws Exception {
        // Mock data
        Long ojkId=1L;
        Ojk ojk = ojk();
        ojk.setOjkId(ojkId);
        OjkReqDto ojkReqDto = ojkReqDto();
        FileUpload fileUpload = mock(FileUpload.class);
        OjkFile ojkFile = new OjkFile();
        List<OjkDepartmentUser> ojkDepartmentUsers = new ArrayList<>();

        // Mock behavior
        when(ojkRepository.findByIdOptional(ojkId)).thenReturn(Optional.of(ojk));
        when(ojkFileService.update(ojkId, fileUpload)).thenReturn(ojkFile);
        when(ojkDepartmentUserService.editOjkDepartmentUser(any(), any())).thenReturn(ojkDepartmentUsers);

        // Test
        OjkResDto result = ojkService.edit(ojkId, ojkReqDto, fileUpload);


        // Verify
        assertNotNull(result);
        assertEquals(ojk.getOjkId(), result.ojkId());
        assertEquals(ojkReqDto.title(), result.title());
    }

}