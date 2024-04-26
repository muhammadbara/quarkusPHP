package org.belajar.api.services.event.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.belajar.api.dto.eventDto.category.CategoryReqDto;
import org.belajar.api.dto.eventDto.category.CategoryResDto;
import org.belajar.api.entity.event.Category;
import org.belajar.api.repository.event.CategoryRepository;
import org.belajar.api.services.event.CategoryService;

import java.util.List;


@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryResDto> getAll(){
       return categoryRepository.findAll().stream().map(category -> new CategoryResDto(category.getCatId(),category.getCategoryName(),category.getDescription(), category.getEvents())).toList();
    }

    @Override
    @Transactional
    public CategoryResDto create(CategoryReqDto categoryReqDto){
        Category category = new Category();
        category.setCategoryName(categoryReqDto.categoryName());
        category.setDescription(categoryReqDto.description());
        categoryRepository.persist(category);

        return new CategoryResDto(category.getCatId(),category.getCategoryName(),category.getDescription(),category.getEvents());
    }

    @Override
    public Category findById(Long id){
       return categoryRepository.findByIdOptional(id).orElseThrow(()-> new RuntimeException("id not available"));
    }

    @Override
    @Transactional
    public CategoryResDto edit(Long id, CategoryReqDto categoryReqDto){
        Category category = findById(id);

        category.setCategoryName(categoryReqDto.categoryName());
        category.setDescription(categoryReqDto.description());
        categoryRepository.persist(category);

        return new CategoryResDto(category.getCatId(),category.getCategoryName(),category.getDescription(),category.getEvents());
    }

    @Override
    @Transactional
    public void delete(Long id){
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
