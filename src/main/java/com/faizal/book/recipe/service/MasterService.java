package com.faizal.book.recipe.service;

import com.faizal.book.recipe.dto.response.CategoryResponse;
import com.faizal.book.recipe.dto.response.LevelResponse;
import com.faizal.book.recipe.model.Category;
import com.faizal.book.recipe.model.Level;
import com.faizal.book.recipe.repository.CategoryRepository;
import com.faizal.book.recipe.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LevelRepository levelRepository;

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse::createFromModel).collect(Collectors.toList());
    }

    public List<LevelResponse> getLevels() {
        return levelRepository.findAll().stream().map(LevelResponse::createFromModel).collect(Collectors.toList());
    }

    public Level getLevelById(Integer id) {
        return levelRepository.findById(id).orElseThrow();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
