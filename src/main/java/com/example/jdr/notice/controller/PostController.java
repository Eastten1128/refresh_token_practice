package com.example.jdr.notice.controller;

import com.example.jdr.notice.dto.PostRequest;
import com.example.jdr.notice.dto.PostResponse;
import com.example.jdr.notice.dto.PostUpdateRequest;
import com.example.jdr.notice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(
            @Valid @RequestBody PostRequest request
    ) {
        return postService.createPost(request);
    }

    // 전체 게시글 조회
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public PostResponse getPost(
            @PathVariable("postId") Long postId
    ) {
        return postService.getPost(postId);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public PostResponse updatePost(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return postService.updatePost(postId, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(
            @PathVariable("postId") Long postId
    ) {
        postService.deletePost(postId);
    }
}