package com.example.jdr.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="posts")
@Getter
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id", nullable= false)
    private User author;
}
