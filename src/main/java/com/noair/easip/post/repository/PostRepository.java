package com.noair.easip.post.repository;

import com.noair.easip.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String>, PostRepositoryCustom {
    Page<Post> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
}
