package com.example.nexo.controller;

import com.example.nexo.dto.CategoryResponseDTO;
import com.example.nexo.dto.CreateCategoryDto;
import com.example.nexo.dto.UpdateCategoryDto;
import com.example.nexo.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
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
