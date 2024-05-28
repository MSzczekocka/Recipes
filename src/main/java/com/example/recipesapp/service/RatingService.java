package com.example.recipesapp.service;

import com.example.recipesapp.entity.Rating;
import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.entity.User;
import com.example.recipesapp.exceptions.RatingBadRequestException;
import com.example.recipesapp.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsWithUser(User user) {
        return ratingRepository.findByUser(user);
    }

    public List<Rating> getRatingsWithRecipe(Recipe recipe) {
        return ratingRepository.findByRecipe(recipe);
    }
    public Rating addRating(Rating newRating) {
        try {
            newRating.setDate(LocalDateTime.now());
            return ratingRepository.save(newRating);
        } catch (Exception exception) {
            throw new RatingBadRequestException();
        }
    }


}
