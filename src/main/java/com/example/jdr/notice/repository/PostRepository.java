package com.example.jdr.notice.repository;

import com.example.jdr.notice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
