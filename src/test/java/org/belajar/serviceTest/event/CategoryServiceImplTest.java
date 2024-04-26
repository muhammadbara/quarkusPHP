package org.belajar.serviceTest.event;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.belajar.api.dto.eventDto.category.CategoryReqDto;
import org.belajar.api.dto.eventDto.category.CategoryResDto;
import org.belajar.api.entity.event.Category;

import org.belajar.api.repository.event.CategoryRepository;
import org.belajar.api.services.event.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@QuarkusTest
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll(){
        List<Category>categoryList=new ArrayList<>();
        categoryList.add(new Category(1L,"cat1",null,null));
        categoryList.add(new Category(2L,"cat2",null,null));

        List<CategoryResDto>categoryResDto= categoryList.stream().map(category -> new CategoryResDto(category.getCatId(),category.getCategoryName(),category.getDescription(),category.getEvents())).toList();

        PanacheQuery<Category> panacheQuery = Mockito.mock(PanacheQuery.class);

        Mockito.when(panacheQuery.stream()).thenReturn(categoryList.stream());
        Mockito.when(categoryRepository.findAll()).thenReturn(panacheQuery);

        List<CategoryResDto> result = categoryService.getAll();

        assertEquals(categoryResDto.size(), result.size());
        assertEquals(categoryResDto.get(0).categoryName(), result.get(0).categoryName());
        assertEquals(categoryResDto.get(1).description(), result.get(1).description());

    }

    @Test
    void testCreate() {
        CategoryReqDto categoryReqDto = new CategoryReqDto("cat3", "Description3");

        Category category = new Category(3L, categoryReqDto.categoryName(), categoryReqDto.description(), null);

        doNothing().when(categoryRepository).persist(any(Category.class));
        Mockito.when(categoryRepository.isPersistent(ArgumentMatchers.any(Category.class))).thenReturn(true);
        CategoryResDto createdDto = categoryService.create(categoryReqDto);

        assertEquals("cat3", createdDto.categoryName());
        assertEquals("Description3", createdDto.description());
    }

    @Test
    void testFindById() {
        Category category = new Category(1L, "cat1", "Description1", null);

        Mockito.when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(1L);

        assertEquals("cat1", foundCategory.getCategoryName());
        assertEquals("Description1", foundCategory.getDescription());
    }

    @Test
    void testFindByIdNotFound() {
        Mockito.when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.findById(1L));
    }

    @Test
    void editCategory() {
        // Persiapan objek ProductReqDto
        CategoryReqDto categoryReqDto = new CategoryReqDto("cat1", "Description1");

        // Persiapan objek Product yang ada dalam repositori
        Category category = new Category(1L, "cat", "des",null);

        Mockito.when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.of(category));

        // Act
        CategoryResDto categoryResDto = categoryService.edit(1L, categoryReqDto);

        // Assert
        assertEquals(1L, categoryResDto.catId());
        assertEquals(categoryReqDto.categoryName(), categoryResDto.categoryName());
        assertEquals(categoryReqDto.description(), categoryResDto.description());

        // Verify that the product was updated in the repository
        verify(categoryRepository).persist(category);
    }

    @Test
    void deleteCategory(){
        // Arrange

        Category category = new Category(1L, "cat", "des",null);

        Mockito.when(categoryRepository.findByIdOptional(1L)).thenReturn(Optional.of(category));

        // Act
        categoryService.delete(1L);

        // Assert
        verify(categoryRepository).delete(category);

    }

}