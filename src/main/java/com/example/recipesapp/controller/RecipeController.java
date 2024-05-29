package com.example.recipesapp.controller;

import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.entity.RecipeView;
import com.example.recipesapp.exceptions.NotAnAuthorException;
import com.example.recipesapp.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/all")
    @JsonView(RecipeView.Get.class)
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = recipeService.getRecipes();
        return new ResponseEntity<>(allRecipes, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @JsonView(RecipeView.Get.class)
    public ResponseEntity<Recipe> getRecipe(@PathVariable final Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Recipe> postRecipe(@JsonView(RecipeView.PostPut.class) @RequestBody Recipe newRecipe,
                                             @AuthenticationPrincipal UserDetails details) {
        newRecipe.setOwner(details.getUsername());
        Recipe recipe = recipeService.addRecipe(newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public HttpStatus deleteRecipe(@PathVariable final Long id, @AuthenticationPrincipal UserDetails details) {
        Recipe recipeToDelete = recipeService.getRecipe(id);

        if (!recipeToDelete.getOwner().equals(details.getUsername()))
            throw new NotAnAuthorException();

        recipeService.deleteRecipe(id);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public HttpStatus editRecipe(@PathVariable final Long id, @JsonView(RecipeView.PostPut.class) @Valid @RequestBody final Recipe recipe,
                                 @AuthenticationPrincipal UserDetails details) {
        Recipe recipeToEdit = recipeService.getRecipe(id);

        if (!recipeToEdit.getOwner().equals(details.getUsername()))
            throw new NotAnAuthorException();

        recipeService.editRecipe(id, recipe);
        return HttpStatus.NO_CONTENT;
    }

//    @PostMapping(value = "{recipeId}/ingredients/add")
//    public ResponseEntity<Recipe> addIngredientToRecipe(@PathVariable final Long recipeId,
//                                                        @RequestBody final String ingredient,
//                                                        @AuthenticationPrincipal UserDetails details) {
//        Recipe recipe = recipeService.getRecipe(recipeId);
//
//        if (!recipe.getOwner().equals(details.getUsername()))
//            throw new NotAnAuthorException();
//
//
//        Recipe recipeToAdd = recipeService.addIngredientToRecipe(recipeId, ingredient);
//        return new ResponseEntity<>(recipeToAdd, HttpStatus.OK);
//    }

    @PostMapping(value = "{recipeId}/steps/add")
    public ResponseEntity<Recipe> addDirectionToRecipe(@PathVariable final Long recipeId,
                                                       @RequestBody final String step,
                                                       @AuthenticationPrincipal UserDetails details) {
        Recipe recipe = recipeService.getRecipe(recipeId);

        if (!recipe.getOwner().equals(details.getUsername()))
            throw new NotAnAuthorException();

        Recipe recipeToAdd = recipeService.addStepToRecipe(recipeId, step);
        return new ResponseEntity<>(recipeToAdd, HttpStatus.OK);
    }

    @GetMapping(value = "/search/", params = "category")
    @JsonView(RecipeView.Get.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestParam String category) {
        List<Recipe> recipesByCategory = recipeService.getRecipeWithCategory(category);
        return new ResponseEntity<>(recipesByCategory, HttpStatus.OK);
    }

    @GetMapping(value = "/search/", params = "name")
    @JsonView(RecipeView.Get.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Recipe>> getRecipesByName(@RequestParam String name) {
        List<Recipe> recipesByCategory = recipeService.getRecipeWithName(name);
        return new ResponseEntity<>(recipesByCategory, HttpStatus.OK);
    }
}
