package com.example.recipesapp.controller;

import com.example.recipesapp.entity.Ingredient;
import com.example.recipesapp.entity.Unit;
import com.example.recipesapp.service.IngredientsService;
import com.example.recipesapp.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientsService ingredientsService;

    @GetMapping("/all")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> allIngredients = ingredientsService.findAllIngredients();
        return new ResponseEntity<>(allIngredients, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Ingredient> addUnit(@RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientsService.saveIngredient(ingredient);
        return new ResponseEntity<>(newIngredient, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Ingredient> getUnit(@PathVariable final Integer id) {
        Ingredient ingredient = ingredientsService.getIngredientWithId(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }
}
