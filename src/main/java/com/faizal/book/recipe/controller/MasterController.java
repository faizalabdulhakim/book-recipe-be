package com.faizal.book.recipe.controller;

import com.faizal.book.recipe.dto.response.CategoryResponse;
import com.faizal.book.recipe.dto.response.LevelResponse;
import com.faizal.book.recipe.service.MasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book-recipe-masters")
@RequiredArgsConstructor
@Slf4j
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping("/category-option-lists")
    public ResponseEntity<Object> getCategories() {
        List<CategoryResponse> categories = masterService.getCategories();

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/level-option-lists")
    public ResponseEntity<Object> getLevels() {
        List<LevelResponse> levels = masterService.getLevels();

        return ResponseEntity.ok().body(levels);
    }
}

