package com.faizal.book.recipe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class AddRecipeRequest {
    @ToString.Exclude

    private MultipartFile image_filename;


    @Pattern(regexp = "^[a-zA-Z]{1,255}$", message = "Recipe name must be between 1 and 255 alphabetic characters.")
    private String recipe_name;

    @NotBlank(message = "How to cook field cannot be blank")
    private String how_to_cook;

    @Max(value = 999, message = "Time to cook must not exceed 999 minutes")
    @Min(value = 1, message = "Time to cook must be at least 1 minute")
    @NotNull(message = "Time to cook cannot be null")
    private Integer time_cook;

    @NotBlank(message = "Ingredient field cannot be blank")
    private String ingredient;

    @NotNull(message = "Category ID cannot be null")
    private Integer category_id;

    @NotNull(message = "Level ID cannot be null")
    private Integer level_id;
}
