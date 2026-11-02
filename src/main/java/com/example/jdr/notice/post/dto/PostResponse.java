package com.example.jdr.notice.post.dto;

import com.example.jdr.notice.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private String authorName;


    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername()
        );
    }
}
