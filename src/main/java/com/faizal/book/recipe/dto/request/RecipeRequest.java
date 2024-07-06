package com.faizal.book.recipe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeRequest {
    @ToString.Exclude
    @NotNull(message = "{NotNull.RecipeRequest.image_filename}")
    private MultipartFile image_filename;

    @NotBlank(message = "{NotBlank.RecipeRequest.recipe_name}")
    @Pattern(regexp = "^[a-zA-Z]{1,255}$", message = "{Pattern.RecipeRequest.recipe_name}")
    private String recipe_name;

    @NotBlank(message = "{NotBlank.RecipeRequest.how_to_cook}")
    private String how_to_cook;

    @Max(value = 999, message = "{Max.RecipeRequest.time_cook}")
    @Min(value = 1, message = "{Min.RecipeRequest.time_cook}")
    @NotNull(message = "{NotNull.RecipeRequest.time_cook}")
    private Integer time_cook;

    @NotBlank(message = "{NotBlank.RecipeRequest.ingredient}")
    private String ingredient;

    @NotNull(message = "{NotNull.RecipeRequest.category_id}")
    private Integer category_id;

    @NotNull(message = "{NotNull.RecipeRequest.level_id}")
    private Integer level_id;
}
