package com.example.recipesapp.repositories;

import com.example.recipesapp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoriesIgnoreCase(String category);
    List<Recipe> findByNameContainsIgnoreCase(String category);


}
