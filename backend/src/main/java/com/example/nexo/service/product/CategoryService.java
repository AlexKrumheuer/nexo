package com.example.nexo.service.product;

import com.example.nexo.dto.product.CategoryResponseDTO;
import com.example.nexo.dto.product.CreateCategoryDto;
import com.example.nexo.dto.product.UpdateCategoryDto;
import com.example.nexo.entity.product.Category;
import com.example.nexo.infra.exception.CategoryException;
import com.example.nexo.repository.product.CategoryRepository;
import com.example.nexo.util.Mapper;
import com.example.nexo.util.SlugUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

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
            slug = SlugUtil.generateSlug(dto.name(), categoryRepository::existsBySlug);
        } else {
            slug = SlugUtil.generateSlug(dto.slug(), categoryRepository::existsBySlug);
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
        return mapper.MapperCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> findAll (Pageable pageable){
        return categoryRepository.findAll(pageable)
                .map(mapper::MapperCategoryResponse);
    }

    public CategoryResponseDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(mapper::MapperCategoryResponse)
                .orElseThrow(()-> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

    }

    public CategoryResponseDTO update(Long id, UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        if (dto.slug() != null && !dto.slug().trim().isEmpty()) {
            String newSlug = SlugUtil.generateSlug(dto.name(), categoryRepository::existsBySlug);
            
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
        return mapper.MapperCategoryResponse(category);
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

    
}
