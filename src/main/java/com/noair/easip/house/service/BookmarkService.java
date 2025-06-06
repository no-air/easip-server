package com.noair.easip.house.service;

import com.noair.easip.house.domain.House;
import com.noair.easip.member.domain.Bookmark;
import com.noair.easip.member.domain.BookmarkId;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public boolean isBookmarked(String memberId, String houseId) {
        return bookmarkRepository.existsByMemberIdAndHouseId(memberId, houseId);
    }

    public void toggleBookmark(Member loginMember, House house) {
        if (isBookmarked(loginMember.getId(), house.getId())) {
            bookmarkRepository.deleteById(new BookmarkId(loginMember.getId(), house.getId()));

        } else {
            bookmarkRepository.save(
                    Bookmark.builder()
                            .id(new BookmarkId(loginMember.getId(), house.getId()))
                            .member(loginMember)
                            .house(house)
                            .build()
            );
        }
    }
}
