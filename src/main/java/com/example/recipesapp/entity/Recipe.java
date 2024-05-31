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

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "categories")
    @NotEmpty
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private List<@NotNull @NotBlank String> categories = new ArrayList<>();

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

    @ElementCollection
    @CollectionTable(name = "steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "steps")
    @NotEmpty
    @JsonView({RecipeView.PostPut.class, RecipeView.Get.class})
    private List<@NotNull @NotBlank String> steps = new ArrayList<>();


    public void addCategory(String category) {
        categories.add(category);
    }

    public void addStep(String step) {
        steps.add(step);
    }

}
