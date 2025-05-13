package com.noair.easip.member.repository;

import com.noair.easip.member.domain.SocialAuth;
import com.noair.easip.member.domain.SocialAuthKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAuthRepository extends JpaRepository<SocialAuth, SocialAuthKey> {
    void deleteAllByMemberId(String memberId);
}

