package org.belajar.api.services.event;

import org.belajar.api.dto.eventDto.category.CategoryReqDto;
import org.belajar.api.dto.eventDto.category.CategoryResDto;
import org.belajar.api.entity.event.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResDto> getAll();
    CategoryResDto create(CategoryReqDto categoryReqDto);
    Category findById(Long id);
    CategoryResDto edit(Long id, CategoryReqDto categoryReqDto);
    void delete(Long id);
}
