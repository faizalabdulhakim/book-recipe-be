package com.faizal.book.recipe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String fullname;
    private String password;
    private String role;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_time")
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;
}