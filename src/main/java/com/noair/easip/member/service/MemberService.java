package com.noair.easip.member.service;

import com.noair.easip.auth.config.properties.SocialLoginProvider;
import com.noair.easip.house.domain.District;
import com.noair.easip.house.service.DistrictService;
import com.noair.easip.member.controller.dto.request.CreateMemberRequest;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.SocialAuth;
import com.noair.easip.member.domain.SocialAuthId;
import com.noair.easip.member.exception.MemberNotFoundException;
import com.noair.easip.member.repository.MemberRepository;
import com.noair.easip.member.repository.SocialAuthRepository;
import com.noair.easip.util.StringGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final SocialAuthRepository socialAuthRepository;
    private final DistrictService districtService;

    @Transactional
    public Member createMember(SocialLoginProvider provider, String identifier, CreateMemberRequest request) {
        District livingDistrict = districtService.getDistrictById(request.livingDistrictId());
        List<District> likingDistricts = request.likingDistrictIds().stream()
                .map(districtService::getDistrictById)
                .toList();

        //사용자 생성
        Member member = Member.builder()
                .id(StringGenerator.generateUlid())
                .name(request.name())
                .dateOfBirth(request.dayOfBirth())
                .livingDistrict(livingDistrict)
                .likingDistricts(likingDistricts)
                .allFamilyMemberCount(request.allFamilyMemberCount())
                .myMonthlySalary(request.myMonthlySalary().doubleValue())
                .familyMemberMonthlySalary(request.familyMemberMonthlySalary().doubleValue())
                .position(request.position())
                .hasCar(request.hasCar())
                .carPrice(request.carPrice())
                .assetPrice(request.assetPrice().doubleValue())
                .build();
        memberRepository.save(member);

        //사용자 소셜 로그인 정보 링크
        SocialAuth socialMember = new SocialAuth(new SocialAuthId(provider, identifier), member);
        socialAuthRepository.save(socialMember);

        return member;
    }

    public Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Optional<Member> getMemberBySocialAuthId(SocialLoginProvider provider, String identifier) {
        SocialAuthId key = new SocialAuthId(provider, identifier);
        return socialAuthRepository
                .findById(key)
                .map(SocialAuth::getMember);
    }

    @Transactional
    public void updateMember(String loginMemberId, CreateMemberRequest request) {
        Member member = getMemberById(loginMemberId);
        member.updateProfile(
                request.name(),
                request.dayOfBirth(),
                districtService.getDistrictById(request.livingDistrictId()),
                request.likingDistrictIds().stream()
                        .map(districtService::getDistrictById)
                        .toList(),
                request.allFamilyMemberCount(),
                request.myMonthlySalary().doubleValue(),
                request.familyMemberMonthlySalary().doubleValue(),
                request.position(),
                request.hasCar(),
                request.carPrice(),
                request.assetPrice().doubleValue()
        );
    }
}
