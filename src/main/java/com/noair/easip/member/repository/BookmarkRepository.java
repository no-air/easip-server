package com.noair.easip.member.repository;

import com.noair.easip.member.domain.Bookmark;
import com.noair.easip.member.domain.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {
    boolean existsByMemberIdAndHouseId(String memberId, String houseId);
}
