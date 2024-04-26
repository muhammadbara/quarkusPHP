package org.belajar.serviceTest.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.runtime.PanacheQueryImpl;
import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.dto.eventDto.event.EventReqDto;
import org.belajar.api.dto.eventDto.event.EventResDto;
import org.belajar.api.entity.event.Category;
import org.belajar.api.entity.event.Event;
import org.belajar.api.entity.event.EventImage;
import org.belajar.api.entity.event.EventTheme;
import org.belajar.api.enumeration.EventEnum;
import org.belajar.api.repository.event.EventRepository;
import org.belajar.api.services.event.CategoryService;
import org.belajar.api.services.event.EventImageService;
import org.belajar.api.services.event.EventThemeService;
import org.belajar.api.services.event.HistoryService;
import org.belajar.api.services.event.implementation.EventServiceImpl;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class EventServiceImplTest {

    @InjectMocks
    EventServiceImpl eventService;

    @Mock
    EventRepository eventRepository;

    @Mock
    EventImageService eventImageService;

    @Mock
    CategoryService categoryService;

    @Mock
    HistoryService historyService;

    @Mock
    EventThemeService eventThemeService;

    public EventServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_willSuccess() throws IOException {
        // Mock input
        EventReqDto eventReqDto = new EventReqDto("Tittle", "Description", "2024-04-16", "2024-04-17", EventEnum.needVolunteer.YES, EventEnum.audience.YES, EventEnum.status.FINISH, 1L, 1L);

        // Mock dependencies
        EventTheme eventTheme = new EventTheme(1L,"et1",null,null);
        Category category = new Category(1L,"cat1",null,null);
        LocalDate startDate = LocalDate.of(2024, 4, 16);
        LocalDate endDate = LocalDate.of(2024, 4, 17);
        EventImage eventImage = new EventImage();

        when(eventThemeService.findById(1L)).thenReturn(eventTheme);
        when(categoryService.findById(1L)).thenReturn(category);

        when(eventImageService.sendUpload(any())).thenReturn(eventImage);
        doNothing().when(eventRepository).persist(any(Event.class));
        Mockito.when(eventRepository.isPersistent(ArgumentMatchers.any(Event.class))).thenReturn(true);

        // Test
        EventResDto createdDto = eventService.create(eventReqDto, null);

        // Verify
        assertNotNull(createdDto);
        assertEquals("Tittle", createdDto.tittle());
        assertEquals("Description", createdDto.description());
        assertEquals(startDate, createdDto.startDate());
        assertEquals(endDate, createdDto.endDate());
        assertEquals(EventEnum.needVolunteer.YES, createdDto.needVolunteer());
        assertEquals(EventEnum.audience.YES, createdDto.audience());
        assertEquals(EventEnum.status.FINISH, createdDto.status());
        assertEquals(1L, createdDto.category().getCatId());
        assertEquals(1L, createdDto.eventTheme().getEtId());
    }

    @Test
    void testCreate_willFailed() {
        // Mock input
        EventReqDto eventReqDto = new EventReqDto("Tittle", "Description", "2024-04-16", "2024-04-17", EventEnum.needVolunteer.YES, EventEnum.audience.YES, EventEnum.status.FINISH, 1L, 1L);

        // Mock dependencies
        when(eventThemeService.findById(1L)).thenThrow(new RuntimeException("Failed to find event theme"));

        // Test and verify
        IOException exception = assertThrows(IOException.class, () -> eventService.create(eventReqDto, null));
        assertEquals("Gagal membuat event: Failed to find event theme", exception.getMessage());

        // Verify that the dependencies were called with the expected arguments
        verify(eventThemeService, times(1)).findById(1L);
        // Verify other dependencies as needed
    }

    @Test
    void testEdit() throws IOException {
        // Mock data
        Long id = 1L;
        EventReqDto eventReqDto = new EventReqDto("Updated Title", "Updated Description", "2024-04-16", "2024-04-17", EventEnum.needVolunteer.YES, EventEnum.audience.YES, EventEnum.status.FINISH, 1L, 1L);

        // Mock entities

        EventTheme eventTheme = new EventTheme(); // Buat instance EventTheme mock
        Category category = new Category(); // Buat instance Category mock
        EventImage eventImage = new EventImage(); // Buat instance EventImage mock
        Event event = new Event();

        // Mock behavior for service method calls
        when(eventRepository.findByIdOptional(id)).thenReturn(Optional.of(event));
        when(eventThemeService.findById(anyLong())).thenReturn(eventTheme);
        when(categoryService.findById(anyLong())).thenReturn(category);
        when(eventImageService.update(anyLong(), any(FileUpload.class))).thenReturn(eventImage);

        // Panggil metode yang akan diuji
        EventResDto editedEvent = eventService.edit(eventReqDto, id, mock(FileUpload.class));

        // Verifikasi bahwa entitas telah diperbarui dengan benar
        assertEquals(eventReqDto.tittle(), event.getTittle());
        assertEquals(eventReqDto.description(), event.getDescription());
        assertEquals(LocalDate.parse(eventReqDto.startDate()), event.getStartDate());
        assertEquals(LocalDate.parse(eventReqDto.endDate()), event.getEndDate());
        assertEquals(eventReqDto.needVolunteer(), event.getNeedVolunteer());
        assertEquals(eventReqDto.audience(), event.getAudience());
        assertEquals(eventReqDto.status(), event.getStatus());
        assertEquals(category, event.getCategory());
        assertEquals(eventTheme, event.getEventTheme());
        assertEquals(eventImage.getImageName(), event.getImagePath());

        // Verifikasi bahwa metode persist dan saveHistory telah dipanggil
        verify(eventRepository).persist(event);
        verify(historyService).saveHistory(EventEnum.action.UPDATE, event);

        // Verifikasi bahwa hasil yang diharapkan sesuai dengan yang dikembalikan oleh metode edit
        assertEquals(editedEvent.tittle(), eventReqDto.tittle());
        assertEquals(editedEvent.description(), eventReqDto.description());
        assertEquals(editedEvent.startDate(), LocalDate.parse(eventReqDto.startDate()));
        assertEquals(editedEvent.endDate(), LocalDate.parse(eventReqDto.endDate()));
        assertEquals(editedEvent.needVolunteer(), eventReqDto.needVolunteer());
        assertEquals(editedEvent.audience(), eventReqDto.audience());
        assertEquals(editedEvent.status(), eventReqDto.status());
        assertEquals(editedEvent.category(), category);
        assertEquals(editedEvent.eventTheme(), eventTheme);
        assertEquals(editedEvent.imagePath(), eventImage.getImageName());
    }

    @Test
    void testFindById() {
        Event event = new Event(1L, "event1", "Description1", null,null,null,null,null,null,null,null,null,null,null,null);

        Mockito.when(eventRepository.findByIdOptional(1L)).thenReturn(Optional.of(event));

        Event foundEvent = eventService.findByid(1L);

        assertEquals("event1", foundEvent.getTittle());
        assertEquals("Description1", foundEvent.getDescription());
    }

    @Test
    void testFindByIdNotFound() {
        Mockito.when(eventRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.findByid(1L));
    }

    @Test
    void deleteEvent(){
        // Arrange

        Event event = event();
        Mockito.when(eventRepository.findByIdOptional(1L)).thenReturn(Optional.of(event));

        // Act
        eventService.delete(1L);

        // Assert
        verify(eventRepository).delete(event);

    }

    Event event(){
        Event event = new Event();
        event.setEventId(1L);
        event.setTittle("Test Event");
        event.setDescription("Test description");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));
        event.setNeedVolunteer(EventEnum.needVolunteer.YES);
        event.setAudience(EventEnum.audience.YES);
        event.setStatus(EventEnum.status.FINISH);
        Category category = new Category();
        category.setCatId(1L);
        event.setCategory(category);
        EventTheme eventTheme = new EventTheme();
        eventTheme.setEtId(1L);
        event.setEventTheme(eventTheme);
        event.setImagePath("test_image.jpg");
        event.setModifyDate(LocalDateTime.now());
        event.setUsers(Collections.emptyList());
        return event;
    }

    @Test
    void testFindByTitle() {
        // Mock data
        String title = "Test Event";
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByTittle(title)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByTitle(title);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByTittle(title);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByTittleNotFound() {
        Mockito.when(eventRepository.findByTittle("Test Event")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByTitle("Test Event"));
    }

    @Test
    void testFindByCategory() {
        // Mock data
        Long categoryId = 1L;
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByCat(categoryId)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByCat(categoryId);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByCat(categoryId);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByCategoryNotFound() {
        Mockito.when(eventRepository.findByCat(9999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByCat(9999L));
    }

    @Test
    void testFindByEventTheme() {
        // Mock data
        Long etId = 1L;
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByEventTheme(etId)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByEventTheme(etId);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByEventTheme(etId);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByEventThemeNotFound() {
        Mockito.when(eventRepository.findByEventTheme(9999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByEventTheme(9999L));
    }

    @Test
    void testFindByNeedVolunteer() {
        // Mock data
        EventEnum.needVolunteer needVolunteer = EventEnum.needVolunteer.YES;
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByVolunteer(needVolunteer)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByNeedVolunteer(needVolunteer);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByVolunteer(needVolunteer);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByNeedVolunteerNotFound() {
        Mockito.when(eventRepository.findByVolunteer(EventEnum.needVolunteer.YES)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByNeedVolunteer(EventEnum.needVolunteer.YES));
    }

    @Test
    void testFindByAudience() {
        // Mock data
        EventEnum.audience audience = EventEnum.audience.YES;
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByAudience(audience)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByAudience(audience);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByAudience(audience);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByAudienceNotFound() {
        Mockito.when(eventRepository.findByAudience(EventEnum.audience.YES)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByAudience(EventEnum.audience.YES));
    }

    @Test
    void testFindByStatus() {
        // Mock data
        EventEnum.status status = EventEnum.status.FINISH;
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByStatus(status)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByStatus(status);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByStatus(status);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByStatusNotFound() {
        Mockito.when(eventRepository.findByStatus(EventEnum.status.FINISH)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByStatus(EventEnum.status.FINISH));
    }

    @Test
    void testFindByStartDate() {
        // Mock data
        String startDate = "2024-10-10";
        LocalDate dateFormat=LocalDate.parse(startDate);
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByStartDate(dateFormat)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByStartDate(startDate);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByStartDate(dateFormat);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByStartDateNotFound() {
        Mockito.when(eventRepository.findByStartDate(LocalDate.now())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByStartDate(LocalDate.now().toString()));
    }

    @Test
    void testFindByEndDate() {
        // Mock data
        String endDate = "2024-10-10";
        LocalDate dateFormat=LocalDate.parse(endDate);
        Event event = event();
        // Mock behavior for eventRepository.findByTittle
        when(eventRepository.findByEndDate(dateFormat)).thenReturn(Optional.of(List.of(event)));

        // Invoke the method to be tested
        List<EventResDto> foundEvents = eventService.findByEndDate(endDate);

        // Verify that eventRepository.findByTittle is called
        verify(eventRepository).findByEndDate(dateFormat);

        // Verify the result
        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());
        EventResDto foundEventDto = foundEvents.get(0);
        assertEquals(event.getEventId(), foundEventDto.eventId());
        assertEquals(event.getTittle(), foundEventDto.tittle());
        assertEquals(event.getDescription(), foundEventDto.description());
        assertEquals(event.getStartDate(), foundEventDto.startDate());
        assertEquals(event.getEndDate(), foundEventDto.endDate());
        assertEquals(event.getNeedVolunteer(), foundEventDto.needVolunteer());
        assertEquals(event.getAudience(), foundEventDto.audience());
        assertEquals(event.getStatus(), foundEventDto.status());
        assertEquals(event.getCategory(), foundEventDto.category());
        assertEquals(event.getEventTheme(), foundEventDto.eventTheme());
        assertEquals(event.getImagePath(), foundEventDto.imagePath());
        assertEquals(event.getModifyDate(), foundEventDto.modifyDate());
        assertEquals(event.getUsers(), foundEventDto.users());
    }

    @Test
    void testFindByEndDateNotFound() {
        Mockito.when(eventRepository.findByEndDate(LocalDate.now())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> eventService.findByEndDate(LocalDate.now().toString()));
    }

    @Test
    public void testGetAll() {
        // Mock data
        Event event1 = new Event(1L, "Event 1", "Description 1", null, null, null, null, null, null, null, null, null, null, null, null);
        Event event2 = new Event(2L, "Event 2", "Description 2", null, null, null, null, null, null, null, null, null, null, null, null);

        List<Event> events = Arrays.asList(event1, event2);

        // Mock behavior
        PanacheQuery<Event> mockQuery = mock(PanacheQuery.class);
        when(eventRepository.findAll()).thenReturn(mockQuery);
        when(mockQuery.page(0, 10)).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(events);

        // Test the method
        List<EventResDto> result = eventService.getAll(0, 10);

        // Assert the result
        assertEquals(events.size(), result.size());

        // Assert individual properties of each EventResDto if needed
        for (int i = 0; i < events.size(); i++) {
            EventResDto expectedDto = convertToEventResDto(events.get(i));
            EventResDto actualDto = result.get(i);
            assertEquals(expectedDto.eventId(), actualDto.eventId());
            assertEquals(expectedDto.tittle(), actualDto.tittle());
            assertEquals(expectedDto.description(), actualDto.description());
            assertEquals(expectedDto.startDate(), actualDto.startDate());
            assertEquals(expectedDto.endDate(), actualDto.endDate());
            assertEquals(expectedDto.needVolunteer(), actualDto.needVolunteer());
            assertEquals(expectedDto.audience(), actualDto.audience());
            assertEquals(expectedDto.status(), actualDto.status());
            assertEquals(expectedDto.category(), actualDto.category());
            assertEquals(expectedDto.eventTheme(), actualDto.eventTheme());
            assertEquals(expectedDto.imagePath(), actualDto.imagePath());
            assertEquals(expectedDto.modifyDate(), actualDto.modifyDate());
            assertEquals(expectedDto.users(), actualDto.users());

        }
    }

    private EventResDto convertToEventResDto(Event event) {
        return new EventResDto(
                event.getEventId(),
                event.getTittle(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getNeedVolunteer(),
                event.getAudience(),
                event.getStatus(),
                event.getCategory(),
                event.getEventTheme(),
                event.getImagePath(),
                event.getModifyDate(),
                event.getUsers());
    }





}