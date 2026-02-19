package com.example.nexo.controller.product;

import com.example.nexo.dto.product.CategoryResponseDTO;
import com.example.nexo.dto.product.CreateCategoryDto;
import com.example.nexo.dto.product.UpdateCategoryDto;
import com.example.nexo.service.product.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CreateCategoryDto dto
    ){
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(
        @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateCategoryDto dto
            ){
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
