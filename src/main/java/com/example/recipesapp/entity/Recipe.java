package com.example.recipesapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private String category;

    @LastModifiedDate
    @JsonView(RecipeView.Get.class)
    LocalDateTime date;

    @NotNull
    @NotBlank
    @Size(max = 1000)
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private String description;


    @JsonIgnore
    private String owner;


//    @ElementCollection
//    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
//    @Column(name = "ingredients")
//    @NotEmpty
//    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
//    private List<@NotNull @NotBlank String> ingredients = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "directions")
    @NotEmpty
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private List<@NotNull @NotBlank String> directions = new ArrayList<>();


//    public void addIngredient(String ingredient) {
//        ingredients.add(ingredient);
//    }

    public void addDirection(String direction) {
        directions.add(direction);
    }

}
