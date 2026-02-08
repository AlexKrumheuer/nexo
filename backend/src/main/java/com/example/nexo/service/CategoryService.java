package com.example.nexo.service;

import com.example.nexo.dto.CategoryResponseDto;
import com.example.nexo.dto.CreateCategoryDto;
import com.example.nexo.dto.UpdateCategoryDto;
import com.example.nexo.entity.Category;
import com.example.nexo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto create(CreateCategoryDto dto) {
        if (categoryRepository.existsBySlug(dto.slug())){
            throw new RuntimeException("Slug já existe");
        }

        Category parent = null;
        if (dto.parentId() != null){
            parent = categoryRepository.findById(dto.parentId())
                    .orElseThrow(() -> new RuntimeException("Categoria pai não encontrada"));
        }

        Category category = Category.builder()
                .name(dto.name())
                .slug(dto.slug())
                .parent(parent)
                .active(dto.active() != null ? dto.active() : true)
                .description(dto.description())
                .imageUrl(dto.imageUrl())
                .displayOrder(dto.displayOrder())
                .build();

        categoryRepository.save(category);
        return toResponse(category);
    }

    public List<CategoryResponseDto> findAll (){
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));

    }

    public CategoryResponseDto update(Long id, UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (dto.name() != null) category.setName(dto.name());
        if (dto.slug() != null) category.setSlug(dto.slug());
        if (dto.active() != null) category.setActive(dto.active());
        if (dto.description() != null) category.setDescription(dto.description());
        if (dto.imageUrl() != null) category.setImageUrl(dto.imageUrl());
        if (dto.displayOrder() != null) category.setDisplayOrder(dto.displayOrder());

        if (dto.parentId() != null){
           Category parent = categoryRepository.findById(dto.parentId())
                   .orElseThrow(()-> new RuntimeException("Categoria pai não encontrada"));
           category.setParent(parent);
        }

        categoryRepository.save(category);
        return toResponse(category);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDto toResponse(Category category){
        return  new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getActive(),
                category.getDescription(),
                category.getImageUrl(),
                category.getDisplayOrder(),
                category.getParent() != null ? category.getParent().getId() : null

        );
    }
}
