package com.faizal.book.recipe.repository;

import com.faizal.book.recipe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
