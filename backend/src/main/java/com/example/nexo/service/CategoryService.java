package com.example.nexo.service;

import com.example.nexo.dto.CategoryResponseDTO;
import com.example.nexo.dto.CreateCategoryDto;
import com.example.nexo.dto.UpdateCategoryDto;
import com.example.nexo.entity.Category;
import com.example.nexo.infra.exception.CategoryException;
import com.example.nexo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CreateCategoryDto dto) {
        // Verify if this category already exists
        if (categoryRepository.existsByName(dto.name())){
            throw new CategoryException("This category already exists", HttpStatus.CONFLICT);
        }

        Category parent = null;
        if (dto.parentId() != null){
            parent = categoryRepository.findById(dto.parentId())
                    .orElseThrow(() -> new CategoryException("This parent category doesn't exists", HttpStatus.NOT_FOUND));
        }
        String slug;
        if(dto.slug() == null || dto.slug().trim().isEmpty()) {
            slug = generateSlug(dto.name());
        } else {
            slug = generateSlug(dto.slug());
        }

        Category category = Category.builder()
                .name(dto.name())
                .slug(slug)
                .parent(parent)
                .active(true)
                .description(dto.description())
                .imageUrl(dto.imageUrl())
                .displayOrder(dto.displayOrder())
                .build();

        categoryRepository.save(category);
        return toResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> findAll (Pageable pageable){
        return categoryRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public CategoryResponseDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(()-> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

    }

    public CategoryResponseDTO update(Long id, UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        if (dto.slug() != null && !dto.slug().trim().isEmpty()) {
            String newSlug = generateSlug(dto.slug());
            
            if (!newSlug.equals(category.getSlug())) {
                if (categoryRepository.existsBySlug(newSlug)) {
                    throw new CategoryException("Slug already in use", HttpStatus.CONFLICT);
                }
                category.setSlug(newSlug);
            }
        }

        if (dto.name() != null) category.setName(dto.name());
        if (dto.active() != null) category.setActive(dto.active());
        if (dto.description() != null) category.setDescription(dto.description());
        if (dto.imageUrl() != null) category.setImageUrl(dto.imageUrl());
        if (dto.displayOrder() != null) category.setDisplayOrder(dto.displayOrder());

        if (dto.parentId() != null){

            if(dto.parentId().equals(id)) {
               throw new CategoryException("Category cannot be its own parent", HttpStatus.BAD_REQUEST);
           }

           Category parent = categoryRepository.findById(dto.parentId())
                   .orElseThrow(()-> new CategoryException("Category parent not found", HttpStatus.NOT_FOUND));
           category.setParent(parent);
        }

        categoryRepository.save(category);
        return toResponse(category);
    }

    public void delete(Long id){
        if (!categoryRepository.existsById(id)) {
            throw new CategoryException("Category not found", HttpStatus.NOT_FOUND);
        }

        if (categoryRepository.existsByParentId(id)) {
            throw new CategoryException("Cannot delete category with sub-categories", HttpStatus.CONFLICT);
        }
 
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO toResponse(Category category){
        Long parent = category.getParent() == null ? null : category.getParent().getId();
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getActive(),
                category.getImageUrl(),
                category.getDisplayOrder(),
                parent
        );
    }

    private String generateSlug(String title) {

        if(title == null || title.trim().isEmpty()) {
            throw new CategoryException("Title for slug is empty", HttpStatus.CONFLICT);
        }


        String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");


        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") 
                .replaceAll("\\s+", "-"); 

        String originalSlug = slug;
        int count = 1;
        while (categoryRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + count;
            count++;
        }
        
        return slug;
    }
}
