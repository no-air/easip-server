package com.noair.easip.member.service;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import com.noair.easip.member.controller.dto.CreateUserDto;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.SocialAuth;
import com.noair.easip.member.domain.SocialAuthId;
import com.noair.easip.member.domain.SocialAuthId;
import com.noair.easip.member.repository.MemberRepository;
import com.noair.easip.member.repository.SocialAuthRepository;
import com.noair.easip.util.StringGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SocialAuthRepository socialAuthRepository;

    @Transactional
    public Member createMember(CreateUserDto createUserDto) {
        //사용자 생성
        Member member = Member.builder()
                .id(StringGenerator.generateUlid())
                .build();
        memberRepository.save(member);

        //사용자 소셜 로그인 정보 링크
        SocialAuth socialMember = new SocialAuth(
                new SocialAuthId(createUserDto.provider(), createUserDto.identifier()),
                member);
        socialAuthRepository.save(socialMember);

        return member;
    }

    public Optional<Member> getMemberBySocialAuthId(SocialLoginProvider provider, String identifier) {
        SocialAuthId key = new SocialAuthId(provider, identifier);
        return socialAuthRepository
                .findById(key)
                .map(SocialAuth::getMember);
    }
}
