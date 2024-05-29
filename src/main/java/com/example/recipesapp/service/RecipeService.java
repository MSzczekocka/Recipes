package com.example.recipesapp.service;

import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.exceptions.RecipeBadRequestException;
import com.example.recipesapp.exceptions.RecipeNotFoundException;
import com.example.recipesapp.repositories.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipesRepository recipesRepository;

    public List<Recipe> getRecipes() {
        List<Recipe> allRecipes = recipesRepository.findAll();
        allRecipes.sort(Comparator.comparing(Recipe::getDate).reversed());
        return allRecipes;
    }

    public List<Recipe> getRecipeWithCategory(String category) {
        List<Recipe> recipesWithCategory = recipesRepository.findByCategoryIgnoreCase(category);
        recipesWithCategory.sort(Comparator.comparing(Recipe::getDate).reversed());
        return recipesWithCategory;
    }

    public List<Recipe> getRecipeWithName(String name) {
        List<Recipe> recipesWithName = recipesRepository.findByNameContainsIgnoreCase(name);
        recipesWithName.sort(Comparator.comparing(Recipe::getDate).reversed());
        return recipesWithName;
    }

    public Recipe getRecipe(Long id) {
        return recipesRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    public Recipe addRecipe(Recipe newRecipe) {
        try {
            newRecipe.setDate(LocalDateTime.now());
            return recipesRepository.save(newRecipe);
        } catch (Exception exception) {
            throw new RecipeBadRequestException();
        }
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = getRecipe(id);
        recipesRepository.delete(recipe);
    }

    public Recipe editRecipe(Long id, @Valid Recipe recipe) {
        Recipe recipeToEdit = getRecipe(id);
        try {
            recipeToEdit.setDescription(recipe.getDescription());
            recipeToEdit.setSteps(recipe.getSteps());
            //recipeToEdit.setIngredients(recipe.getIngredients());
            recipeToEdit.setName(recipe.getName());
            recipeToEdit.setCategory(recipe.getCategory());
            recipeToEdit.setDate(LocalDateTime.now());
            recipesRepository.save(recipeToEdit);
            return recipeToEdit;
        } catch (RuntimeException exception) {
            throw new RecipeBadRequestException();
        }
    }

    @Transactional
    public Recipe addStepToRecipe(Long recipeId, String step) {
        Recipe recipe = getRecipe(recipeId);
        recipe.setDate(LocalDateTime.now());
        recipe.addStep(step);
        return recipe;
    }

//    @Transactional
//    public Recipe addIngredientToRecipe(Long recipeId, String ingredient) {
//        Recipe recipe = getRecipe(recipeId);
//        recipe.setDate(LocalDateTime.now());
//        recipe.addIngredient(ingredient);
//        return recipe;
//    }

}
