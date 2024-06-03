package com.example.recipesapp.controller;


import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.entity.Tag;
import com.example.recipesapp.service.RecipeService;
import com.example.recipesapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> allTags = tagService.getTags();
        return new ResponseEntity<>(allTags, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        Tag newTag = tagService.addTag(tag);
        return ResponseEntity.ok(newTag);
    }

}