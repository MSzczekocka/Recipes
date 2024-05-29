package com.example.recipesapp.service;

import com.example.recipesapp.entity.Comment;
import com.example.recipesapp.entity.Favourite;
import com.example.recipesapp.entity.Recipe;
import com.example.recipesapp.entity.User;
import com.example.recipesapp.exceptions.CommentBadRequestException;
import com.example.recipesapp.exceptions.FavouriteBadRequestException;
import com.example.recipesapp.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsWithUser(User user) {
        return commentRepository.findByUser(user);
    }
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsWithRecipe(Recipe recipe) {
        return commentRepository.findByRecipe(recipe);
    }

    public Comment addComment(Comment newComment) {
        try {
            newComment.setDate(LocalDateTime.now());
            return commentRepository.save(newComment);
        } catch (Exception exception) {
            throw new CommentBadRequestException();
        }
    }

}
