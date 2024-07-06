package com.faizal.book.recipe.controller;

import com.faizal.book.recipe.dto.request.AddRecipeRequest;
import com.faizal.book.recipe.dto.request.GetRecipesQueryParams;
import com.faizal.book.recipe.dto.request.RecipeRequest;
import com.faizal.book.recipe.dto.response.RecipeResponse;
import com.faizal.book.recipe.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/book-recipes")
    public ResponseEntity<Object> getBookRecipes(GetRecipesQueryParams params) {
        List<RecipeResponse> bookRecipes = recipeService.getBookRecipes(params);

        return new ResponseEntity<>(bookRecipes, HttpStatus.OK);
    }

    @GetMapping("/book-recipes/{recipe_id}")
    public ResponseEntity<Object> getRecipeDetail(@PathVariable Integer recipe_id) {
        RecipeResponse recipe = recipeService.getRecipeDetail(recipe_id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/book-recipes")
    public ResponseEntity<Object> addRecipe(@Valid @RequestBody AddRecipeRequest request) {
        RecipeResponse recipe = RecipeResponse.createFromModel(recipeService.addRecipe(request));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PutMapping("/book-recipes/{recipe_id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable Integer recipe_id, @Valid @RequestBody RecipeRequest request) {
        RecipeResponse recipe = RecipeResponse.createFromModel(recipeService.updateRecipe(recipe_id, request));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping("/book-recipes/{recipe_id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Integer recipe_id) {
        RecipeResponse recipe = RecipeResponse.createFromModel(recipeService.deleteRecipe(recipe_id));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PutMapping("/book-recipes/{recipe_id}/favorites")
    public ResponseEntity<Object> addRecipeToFavorites(@PathVariable("recipe_id") Integer recipeId) {
        RecipeResponse recipe = RecipeResponse.createFromModel(recipeService.addRecipeToFavorites(recipeId));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

}
