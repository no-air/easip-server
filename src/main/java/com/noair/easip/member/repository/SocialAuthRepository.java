package com.noair.easip.member.repository;

import com.noair.easip.member.domain.SocialAuth;
import com.noair.easip.member.domain.SocialAuthId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAuthRepository extends JpaRepository<SocialAuth, SocialAuthId> {
    void deleteAllByMemberId(String memberId);
}

