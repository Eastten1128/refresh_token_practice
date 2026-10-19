package com.example.jdr.notice.service;

import com.example.jdr.notice.dto.PostRequest;
import com.example.jdr.notice.dto.PostResponse;
import com.example.jdr.notice.dto.PostUpdateRequest;
import com.example.jdr.notice.entity.Post;
import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.repository.PostRepository;
import com.example.jdr.notice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public PostResponse createPost(PostRequest request) {
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() ->
                        new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                author
        );

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    // 전체 게시글 조회
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    // 게시글 ID로 상세 조회
    public PostResponse getPost(Long id) {
        Post post = findPostOrThrow(id);

        return PostResponse.from(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = findPostOrThrow(id);

        post.update(request.getTitle(), request.getContent());

        return PostResponse.from(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = findPostOrThrow(id);

        postRepository.delete(post);
    }

    private Post findPostOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));
    }
}