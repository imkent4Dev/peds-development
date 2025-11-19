package com.lyhorng.pedssystem.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.property.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}