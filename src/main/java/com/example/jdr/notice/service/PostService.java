package com.example.jdr.notice.service;

import com.example.jdr.notice.entity.Post;
import com.example.jdr.notice.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //게시글 목록(제목만 추출)
    public List<Post> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .toList();
    }

    // 게시글 상세
    public Post getPost(Long id){
        return postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}

