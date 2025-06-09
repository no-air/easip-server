package com.noair.easip.post.repository;

import com.noair.easip.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> searchLikingPosts(
            String keyword,
            String loginMemberId,
            Pageable pageable
    );

    Page<Post> searchHomePosts(Pageable pageable);
}
