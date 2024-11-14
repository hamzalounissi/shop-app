package com.code.shopapp.controller;

import com.code.shopapp.exceptions.RessourceNotFoundException;
import com.code.shopapp.model.Category;
import com.code.shopapp.response.ApiResponse;
import com.code.shopapp.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok().body(new ApiResponse("Found", categories));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category theCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            Category updateCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
