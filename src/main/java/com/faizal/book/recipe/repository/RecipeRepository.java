package com.faizal.book.recipe.repository;

import com.faizal.book.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
    Optional<Recipe> findByIdAndUserIdAndIsDeleted(Integer id, Integer userId, Boolean isDeleted);
}

