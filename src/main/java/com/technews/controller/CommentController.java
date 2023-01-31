package com.technews.controller;

import com.technews.model.Comment;
import com.technews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentRepository repository;

    @GetMapping("/api/comments")
    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    public Comment getComment(@PathVariable int id) {
        return repository.getReferenceById(id);
    }

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        return repository.save(comment);
    }

    @PutMapping("/api/updateComment/{id}")
    public Comment updateComment(@PathVariable int id, @RequestBody Comment comment) {
        Comment tempComment = repository.getReferenceById(id);
        tempComment.setCommentText(comment.getCommentText());
        return repository.save(tempComment);
    }

    @DeleteMapping("/api/comments/{id}")
    public void deleteComment(@PathVariable int id) {
        repository.deleteById(id);
    }
}
