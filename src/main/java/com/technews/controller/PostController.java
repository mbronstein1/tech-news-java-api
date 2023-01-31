package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.model.Vote;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        List<Post> postList = repository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return postList;
    }

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Integer id) {
        Post returnPost = repository.getReferenceById(id);
        returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));
        return returnPost;
    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post) {
        repository.save(post);
        return post;
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        Post tempPost = repository.getReferenceById(id);
        tempPost.setTitle(post.getTitle());
        return repository.save(tempPost);
    }

    @PutMapping("/api/posts/upvote")
    public String addVote(@RequestBody Vote vote, HttpServletRequest request) {
        String returnValue = "";

        // if the user has session data (meaning if the user is logged in)...
        if (request.getSession(false) != null) {
            Post returnPost = null;

            // ...then get session user data and set the userId of the vote param to the
            // session id
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            vote.setUserId(sessionUser.getId());
            voteRepository.save(vote);

            // I don't understand this fully
            // Looks like we are finding the post id that correlates to the vote param post
            // id
            // Then we are setting the vote count for that post
            returnPost = repository.getReferenceById(vote.getPostId());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(vote.getPostId()));

            returnValue = "";
        } else {
            returnValue = "login";
        }
        return returnValue;
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        repository.deleteById(id);
    }
}
