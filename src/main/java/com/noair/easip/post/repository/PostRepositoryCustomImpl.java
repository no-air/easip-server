package com.noair.easip.post.repository;

import com.noair.easip.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.noair.easip.member.domain.QPostScheduleNotification.postScheduleNotification;
import static com.noair.easip.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Post> searchLikingPosts(String keyword, String loginMemberId, Pageable pageable) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(
                        post.title.containsIgnoreCase(keyword),
                        post.schedules.any().id.in(
                                queryFactory.select(postScheduleNotification.id.postScheduleId)
                                        .from(postScheduleNotification)
                                        .where(postScheduleNotification.id.memberId.eq(loginMemberId))
                                        .fetch()
                        ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.title.containsIgnoreCase(keyword),
                        post.schedules.any().id.in(
                                queryFactory.select(postScheduleNotification.id.postScheduleId)
                                        .from(postScheduleNotification)
                                        .where(postScheduleNotification.id.memberId.eq(loginMemberId))
                                        .fetch()
                        ))
                .fetchOne();

        return new PageImpl<>(posts, pageable, total != null ? total : 0L);
    }
}
