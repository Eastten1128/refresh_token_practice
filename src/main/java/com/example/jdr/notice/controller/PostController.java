package com.example.jdr.notice.controller;

import com.example.jdr.notice.entity.Post;
import com.example.jdr.notice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping({"/postId"})
    public Post getPost(@PathVariable long postId){
        return postService.getPost(postId);
    }
}
