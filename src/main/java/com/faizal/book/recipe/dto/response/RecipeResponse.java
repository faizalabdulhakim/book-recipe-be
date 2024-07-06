package com.faizal.book.recipe.dto.response;

import com.faizal.book.recipe.model.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeResponse {
    @JsonProperty("recipe_id")
    private Integer recipeId;

    @JsonProperty("recipe_name")
    private String recipeName;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("time_cook")
    private Integer timeCook;

    private String ingredient;

    @JsonProperty("how_to_cook")
    private String howToCook;

    @JsonProperty("is_favorite")
    private Boolean isFavorite;

    private CategoryResponse categories;

    private LevelResponse levels;

    public static RecipeResponse createFromModel(Recipe recipe) {
        CategoryResponse categoryResponse = CategoryResponse.createFromModel(recipe.getCategory());
        LevelResponse levelResponse = LevelResponse.createFromModel(recipe.getLevel());
        Boolean isFavorite = Boolean.FALSE;
        if (recipe.getFavoriteFood() != null) {
            isFavorite = recipe.getFavoriteFood().getIsFavorite();
        }

        return RecipeResponse.builder()
                .recipeId(recipe.getId())
                .recipeName(recipe.getRecipeName())
                .imageUrl(recipe.getImageFilename())
                .timeCook(recipe.getTimeCook())
                .ingredient(recipe.getIngredient())
                .howToCook(recipe.getHowToCook())
                .isFavorite(isFavorite)
                .categories(categoryResponse)
                .levels(levelResponse)
                .build();
    }

    public static RecipeResponse createGetRecipesResponse(Recipe recipe) {
        RecipeResponse recipeResponse = createFromModel(recipe);

        // unnecessary fields
        recipeResponse.setHowToCook(null);
        recipeResponse.setIngredient(null);

        return recipeResponse;
    }

    public static RecipeResponse createGetRecipeDetailResponse(Recipe recipe) {
        return createFromModel(recipe);
    }
}