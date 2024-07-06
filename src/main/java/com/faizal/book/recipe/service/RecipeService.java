package com.faizal.book.recipe.service;

import com.faizal.book.recipe.dto.request.AddRecipeRequest;
import com.faizal.book.recipe.dto.request.GetRecipesQueryParams;
import com.faizal.book.recipe.dto.request.RecipeRequest;
import com.faizal.book.recipe.dto.response.RecipeResponse;
import com.faizal.book.recipe.exception.ApiRequestException;
import com.faizal.book.recipe.model.Category;
import com.faizal.book.recipe.model.Level;
import com.faizal.book.recipe.model.Recipe;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.repository.CategoryRepository;
import com.faizal.book.recipe.repository.LevelRepository;
import com.faizal.book.recipe.repository.RecipeRepository;
import com.faizal.book.recipe.repository.RecipeSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeService {
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LevelRepository levelRepository;

    private MasterService masterService;
    private MinioService minIo;

    public List<RecipeResponse> getBookRecipes(GetRecipesQueryParams params) {
        try {
            Specification<Recipe> specification = Specification.where(RecipeSpecification.notDeleted());
            Pageable pageable = PageRequest.of(0, 10);

            if (params != null) {
                if (params.getUserId() != null) {
                    specification = specification.and(RecipeSpecification.userId(params.getUserId()));
                }

                if (params.getRecipeName() != null) {
                    specification = specification.and(RecipeSpecification.recipeNameLike(params.getRecipeName()));
                }

                if (params.getLevelId() != null) {
                    specification = specification.and(RecipeSpecification.levelId(params.getLevelId()));
                }

                if (params.getCategoryId() != null) {
                    specification = specification.and(RecipeSpecification.categoryId(params.getCategoryId()));
                }

                if (params.getSortBy() != null) {
                    if (!params.getSortBy().isEmpty()) {
                        specification = RecipeSpecification.orderBy(specification, params.getSortBy());
                    }
                } else {
                    params.setSortBy(List.of("recipeName", "asc"));
                    specification = RecipeSpecification.orderBy(specification, params.getSortBy());
                }

                if (params.getPageSize() != null && params.getPageSize() > 0 && params.getPageNumber() != null && params.getPageNumber() >= 0) {
                    pageable = PageRequest.of(params.getPageNumber(), params.getPageSize());
                }
            }

            return recipeRepository.findAll(specification, pageable).stream().map(RecipeResponse::createGetRecipesResponse).collect(Collectors.toList());

        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Transactional
    public Recipe addRecipe(AddRecipeRequest request) {
        Level level = masterService.getLevelById(request.getLevel_id());
        Category category = masterService.getCategoryById(request.getCategory_id());

        String fileType = request.getImage_filename().getContentType().split("/")[1];
        Long time = Instant.now().getEpochSecond();
        String imageFilename = String.format("%s_%s_%s.%s", request.getRecipe_name(), level.getLevelName(), time, fileType);

        String url_file = minIo.uploadFile(request.getImage_filename(), imageFilename);
        log.info("Image uploaded to MinIO: {}", url_file);

        Recipe recipe = Recipe.builder()
                .recipeName(request.getRecipe_name())
                .level(level)
                .category(category)
                .imageFilename(imageFilename)
                .build();
        return recipeRepository.save(recipe);
    }

    public RecipeResponse getRecipeDetail(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeId));
        log.info("Recipe found: {}", recipe);
        return RecipeResponse.createGetRecipeDetailResponse(recipe);
    }

    public Recipe updateRecipe(Integer recipeId, RecipeRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeId));

        Level level = masterService.getLevelById(request.getLevel_id());
        Category category = masterService.getCategoryById(request.getCategory_id());

        String fileType = request.getImage_filename().getContentType().split("/")[1];
        Long time = Instant.now().getEpochSecond();
        String imageFilename = String.format("%s_%s_%s.%s", request.getRecipe_name(), level.getLevelName(), time, fileType);

        String url_file = minIo.uploadFile(request.getImage_filename(), imageFilename);
        log.info("Image uploaded to MinIO: {}", url_file);

        recipe.setRecipeName(request.getRecipe_name());
        recipe.setCategory(category);
        recipe.setLevel(level);
        recipe.setImageFilename(imageFilename);
        recipe.setTimeCook(request.getTime_cook());
        recipe.setHowToCook(request.getHow_to_cook());
        recipe.setIngredient(request.getIngredient());

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return updatedRecipe;
    }

    public Recipe deleteRecipe(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe dengan id " + recipeId + " tidak ditemukan "));
        recipe.setIsDeleted(Boolean.TRUE);
        return recipeRepository.save(recipe);
    }

    public Recipe addRecipeToFavorites(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeId));
        return recipeRepository.save(recipe);
    }


}
