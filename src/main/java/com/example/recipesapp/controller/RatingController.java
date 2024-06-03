package com.example.recipesapp.controller;

import com.example.recipesapp.dto.RatingDto;
import com.example.recipesapp.entity.Rating;
import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.entity.User;
import com.example.recipesapp.service.RatingService;
import com.example.recipesapp.service.RecipeService;
import com.example.recipesapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final RecipeService recipeService;

    @GetMapping(value = "/my-ratings")
    public ResponseEntity<List<Rating>> getAllRatingsForUser(@AuthenticationPrincipal UserDetails details) {
        User user = userService.findByEmail(details.getUsername());
        List<Rating> allRecipesWithUser = ratingService.getRatingsWithUser(user);
        return new ResponseEntity<>(allRecipesWithUser, HttpStatus.OK);
    }

    @GetMapping(value = "/search/", params = "recipeId")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Rating>> getRatingsForRecipe(@RequestParam Long recipeId) {
        Recipe recipeWithId = recipeService.getRecipe(recipeId);
        List<Rating> allRatingsForRecipe = ratingService.getRatingsWithRecipe(recipeWithId);
        return new ResponseEntity<>(allRatingsForRecipe, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> allRecipes = ratingService.getRatings();
        return new ResponseEntity<>(allRecipes, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Rating> postRating(@RequestBody RatingDto ratingDto,
                                             @AuthenticationPrincipal UserDetails details) {
        User user = userService.findByEmail(details.getUsername());
        Recipe recipe = recipeService.getRecipe(ratingDto.getRecipeId());

        Rating newRating = new Rating();
        newRating.setUser(user);
        newRating.setRecipe(recipe);
        newRating.setRating(ratingDto.getRating());

        Rating rating = ratingService.addRating(newRating);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
}
