package com.faizal.book.recipe.repository;


import com.faizal.book.recipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);
    List<User> findAllByIsDeleted(Boolean isDeleted);
    List<User> findAllByIsDeletedAndId(Boolean isDeleted, Long id);
}
