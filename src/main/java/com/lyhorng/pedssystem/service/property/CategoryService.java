package com.lyhorng.pedssystem.service.property;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.property.Category;
import com.lyhorng.pedssystem.repository.property.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    // List
    public Page<Category> getAllCategory(int page, int size) {
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return categoryRepository.findAll(pageable);
    }

    // View
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category is Not Found ID :" + id));
    }

    // Create
    public Category createCategory(String categoryName) {
        Category category = new Category();
        category.setCategory(categoryName);
        return categoryRepository.save(category);
    }

    // Update
    public Category updateCategory(Long id, String categoryName) {
        Optional<Category> exitingCategory = categoryRepository.findById(id);
        if (exitingCategory.isPresent()) {
            Category category = exitingCategory.get();
            category.setCategory(categoryName);
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category is not found ID:" + id);
        }

    }
    // Delete

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
