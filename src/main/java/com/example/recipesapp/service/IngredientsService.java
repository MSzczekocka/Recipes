package com.example.recipesapp.service;

import com.example.recipesapp.entity.Ingredient;
import com.example.recipesapp.entity.Unit;
import com.example.recipesapp.exceptions.IngredientBadRequestException;
import com.example.recipesapp.exceptions.IngredientNotFoundException;
import com.example.recipesapp.exceptions.UnitBadRequestException;
import com.example.recipesapp.exceptions.UnitNotFoundException;
import com.example.recipesapp.repositories.IngredientRepository;
import com.example.recipesapp.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientWithId(Integer id) {
        return ingredientRepository.findById(id).orElseThrow(IngredientNotFoundException::new);
    }

    public Ingredient saveIngredient(Ingredient newIngredient){
        try {
            return ingredientRepository.save(newIngredient);
        } catch (Exception exception) {
            throw new IngredientBadRequestException();
        }
    }
}
