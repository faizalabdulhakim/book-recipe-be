package com.faizal.book.recipe.repository;

import com.faizal.book.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RecipeSpecification {
    public static Specification<Recipe> orderBy(Specification<Recipe> spec, List<String> orderBySpec) {
        return ((root, query, criteriaBuilder) -> {
            if (orderBySpec.get(1).equals("asc")) {
                query.orderBy(criteriaBuilder.asc(root.get(orderBySpec.get(0))));
                return spec.toPredicate(root, query, criteriaBuilder);
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderBySpec.get(0))));
                return spec.toPredicate(root, query, criteriaBuilder);
            }
        });
    }

    public static Specification<Recipe> notDeleted() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), Boolean.FALSE));
    }

    public static Specification<Recipe> userId(Integer userId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("user").get("id"), userId));
    }

    public static Specification<Recipe> recipeNameLike(final String recipeName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("recipeName")),
                criteriaBuilder.lower(
                        criteriaBuilder.literal("%" + recipeName + "%"))
        ));
    }

    public static Specification<Recipe> categoryId(final Integer categoryId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    public static Specification<Recipe> levelId(final Integer levelId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("level").get("id"), levelId));
    }
}