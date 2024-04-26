package org.belajar.serviceTest.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.belajar.api.dto.eventDto.category.CategoryResDto;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeResDto;
import org.belajar.api.entity.event.Category;
import org.belajar.api.entity.event.EventTheme;
import org.belajar.api.repository.event.CategoryRepository;
import org.belajar.api.repository.event.EventThemeRepository;
import org.belajar.api.services.event.implementation.CategoryServiceImpl;
import org.belajar.api.services.event.implementation.EventThemeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventThemeServiceImplTest {
    @InjectMocks
    EventThemeServiceImpl eventThemeService;

    @Mock
    EventThemeRepository eventThemeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll(){
        List<EventTheme> eventThemeList=new ArrayList<>();
        eventThemeList.add(new EventTheme(1L,"et1",null,null));
        eventThemeList.add(new EventTheme(2L,"et2",null,null));

        List<EventThemeResDto>eventThemeResDto= eventThemeList.stream().map(eventTheme -> new EventThemeResDto(eventTheme.getEtId(),eventTheme.getName(),eventTheme.getDescription(),eventTheme.getEvents())).toList();

        PanacheQuery<EventTheme> panacheQuery = Mockito.mock(PanacheQuery.class);

        Mockito.when(panacheQuery.stream()).thenReturn(eventThemeList.stream());
        Mockito.when(eventThemeRepository.findAll()).thenReturn(panacheQuery);

        List<EventThemeResDto> result = eventThemeService.getAll();

        assertEquals(eventThemeResDto.size(), result.size());
        assertEquals(eventThemeList.get(0).getName(), result.get(0).name());
        assertEquals(eventThemeResDto.get(1).description(), result.get(1).description());

    }


}