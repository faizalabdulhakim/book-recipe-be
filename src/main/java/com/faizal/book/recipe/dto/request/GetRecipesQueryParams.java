package com.faizal.book.recipe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GetRecipesQueryParams {
    private Integer userId;
    private String recipeName;
    private Integer levelId;
    private Integer categoryId;
    private List<String> sortBy;
    private Integer pageSize;
    private Integer pageNumber;
}
