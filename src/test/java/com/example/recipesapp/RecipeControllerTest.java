package com.example.recipesapp;

import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class RecipeControllerTest {
    public static Recipe getFirstRecipe(){
        List<String> ingredients = new ArrayList<>();
        ingredients.add("ingredient1");
        ingredients.add("ingredient2");
        List<String> directions = new ArrayList<>();
        ingredients.add("directions1");
        ingredients.add("directions2");

        Recipe recipe = new Recipe();
        recipe.setName("Pizza Princess");
        recipe.setCategory("Main Course");
        recipe.setDate(LocalDateTime.now());
        recipe.setDescription("How to make Pizza Princess");
        recipe.setOwner("user1");
        recipe.setIngredients(ingredients);
        recipe.setDirections(directions);
        return recipe;
    }

    public static Recipe getSecondRecipe(){
        List<String> ingredients = new ArrayList<>();
        ingredients.add("ingredient1");
        ingredients.add("ingredient2");
        List<String> directions = new ArrayList<>();
        ingredients.add("directions1");
        ingredients.add("directions2");

        Recipe recipe = new Recipe();
        recipe.setName("Avocado Toast");
        recipe.setCategory("Breakfast");
        recipe.setDate(LocalDateTime.now());
        recipe.setDescription("How to make Avocado Toast");
        recipe.setOwner("user1");
        recipe.setIngredients(ingredients);
        recipe.setDirections(directions);
        return recipe;
    }

    public static List<Recipe> getAllRecipe() {
        List<Recipe> allRecipe = new ArrayList<>();
        allRecipe.add(getFirstRecipe());
        allRecipe.add(getSecondRecipe());
        return allRecipe;
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void getRecipeByIdTest() throws Exception {

        Recipe recipe = getFirstRecipe();

        when(recipeService.getRecipe(anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8881/api/recipe/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pizza Princess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Main Course"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("How to make Pizza Princess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(recipe.getIngredients()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.directions").value(recipe.getDirections()))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRecipeTest() throws Exception {

        List<Recipe> allRecipe = getAllRecipe();

        doReturn(allRecipe).when(recipeService).getRecipes();

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8881/api/recipe/all"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pizza Princess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Main Course"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("How to make Pizza Princess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ingredients").value(allRecipe.get(0).getIngredients()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].directions").value(allRecipe.get(0).getDirections()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Avocado Toast"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].category").value("Breakfast"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("How to make Avocado Toast"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ingredients").value(allRecipe.get(1).getIngredients()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].directions").value(allRecipe.get(1).getDirections()))
                .andExpect(status().isOk());
    }

    @Test
    public void addRecipeTest() throws Exception {

        Recipe recipe = getFirstRecipe();
        recipe.setId(11L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        when(recipeService.addRecipe(any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8881/api/recipe/new")
                        .content(mapper.writeValueAsString(recipe))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pizza Princess"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Main Course"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("How to make Pizza Princess"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(recipe.getIngredients()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.directions").value(recipe.getDirections()))
                        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1")
    public void deleteRecipeTest() throws Exception {

        Recipe recipe = getFirstRecipe();
        when(recipeService.getRecipe(anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8881/api/recipe/2525")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "user2")
    public void deleteRecipeWrongOwnerTest() throws Exception {

        Recipe recipe = getFirstRecipe();
        when(recipeService.getRecipe(anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8881/api/recipe/2525")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());
    }

//    @Test
//    @WithMockUser(username = "user1")
//    public void editRecipeTest() throws Exception {
//        Recipe recipe = getFirstRecipe();
//        when(recipeService.editRecipe(anyLong(), any(Recipe.class))).thenReturn(recipe);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//
//        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8881/api/recipe/114")
//                        .content(mapper.writeValueAsString(recipe))
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isNoContent());
//    }

//    @Test
//    @WithMockUser(username = "user1")
//    public void addIngredientToRecipeTest() throws Exception {
//        Recipe recipe = getFirstRecipe();
//        Recipe recipeWithNewIngredient = getFirstRecipe();
//        recipeWithNewIngredient.addIngredient("ingredient3");
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//
//        when(recipeService.getRecipe(anyLong())).thenReturn(recipe);
//        when(recipeService.addDirectionToRecipe(anyLong(),anyString())).thenReturn(recipeWithNewIngredient);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8881/api/recipe/1/ingredients/add")
//                        .content(mapper.writeValueAsString(recipeWithNewIngredient))
//                        .content(mapper.writeValueAsString(recipe))
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pizza Princess"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Main Course"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("How to make Pizza Princess"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(recipeWithNewIngredient.getIngredients()))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.directions").value(recipeWithNewIngredient.getDirections()))
//                        .andExpect(status().isOk());
//
//    }

}
