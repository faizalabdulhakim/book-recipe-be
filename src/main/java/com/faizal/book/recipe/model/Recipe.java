package com.faizal.book.recipe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    private FavoriteFood favoriteFood;

    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "image_filename")
    private String imageFilename;

    @Column(name = "time_cook")
    private Integer timeCook;

    private String ingredient;

    @Column(name = "how_to_cook")
    private String howToCook;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_time")
    @CreatedDate
    private Timestamp createdTime;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_time")
    @LastModifiedDate
    private Timestamp modifiedTime;

}
