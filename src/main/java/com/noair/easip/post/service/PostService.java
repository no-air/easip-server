package com.noair.easip.post.service;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.house.domain.House;
import com.noair.easip.house.repository.HouseRepository;
import com.noair.easip.house.service.HouseImageService;
import com.noair.easip.house.service.HouseService;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.Position;
import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import com.noair.easip.post.controller.dto.PostSummaryResponse;
import com.noair.easip.post.controller.dto.ScheduleDto;
import com.noair.easip.post.domain.Post;
import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.SUBSCRIPTION_STATE;
import com.noair.easip.post.exception.PostNotFoundException;
import com.noair.easip.post.repository.PostHouseRepository;
import com.noair.easip.post.repository.PostRepository;
import com.noair.easip.post.repository.PostScheduleRepository;
import com.noair.easip.util.PaginationDto;
import com.noair.easip.util.PriceStringConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.noair.easip.util.PriceStringConvertor.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHouseService postHouseService;
    private final PostScheduleRepository postScheduleRepository;
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;
    private final HouseService houseService;
    private final HouseImageService houseImageService;
    private final MemberService memberService;

    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public String getSubscriptionState(String postId) {
        if (postScheduleRepository.existsByPost_IdAndStartDateTimeLessThan(postId, LocalDateTime.now())) {
            return SUBSCRIPTION_STATE.SCHEDULED.name();
        } else if (postScheduleRepository.existsByPost_IdAndStartDateTimeGreaterThan(postId, LocalDateTime.now())) {
            return SUBSCRIPTION_STATE.COMPLETED.name();
        } else {
            return SUBSCRIPTION_STATE.ONGOING.name();
        }
    }

    public List<ApplicationConditionDto> makeIncomeConditionDto(Post post, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        if (Boolean.TRUE.equals(post.getIsIncomeLimited())) {
            String content = String.format("소득기준 %s원 이내(1인가구 기준)", toKoreanPriceString(post.getIncomeLimit1Person()));
            boolean applicable = loginMember.getMyMonthlySalary() + loginMember.getFamilyMemberMonthlySalary() <= post.getIncomeLimit(loginMember.getAllFamilyMemberCount());
            dto.add(ApplicationConditionDto.of(content, applicable));

        } else {
            dto.add(ApplicationConditionDto.of("소득 무관", true));
        }

        return dto;
    }

    public List<ApplicationConditionDto> makeCarPriceConditionDto(Post post, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        if (Boolean.TRUE.equals(post.getIsCarPriceLimited())) {
            String carPriceContent = String.format("자동차가액 %s원 이내", toKoreanPriceString(post.getCarPriceLimit()));
            boolean carPriceApplicable = loginMember.getCarPrice() <= post.getCarPriceLimit();
            dto.add(ApplicationConditionDto.of(carPriceContent, carPriceApplicable));
        }

        return dto;
    }

    public List<ApplicationConditionDto> makeAssetConditionDtos(Post post, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        if (Boolean.TRUE.equals(post.getIsAssetLimited())) {
            if (post.getYoungManAssetLimit() != null) {
                String assetContent = String.format("청년 자산기준 %s원 이내", toKoreanPriceString(post.getYoungManAssetLimit()));
                boolean assetApplicable = loginMember.getPosition().equals(Position.YOUNG_MAN) && loginMember.getAssetPrice() <= post.getYoungManAssetLimit();
                dto.add(ApplicationConditionDto.of(assetContent, assetApplicable));
            }
            if (post.getNewlyMarriedCoupleAssetLimit() != null) {
                String assetContent = String.format("신혼부부 자산기준 %s원 이내", toKoreanPriceString(post.getNewlyMarriedCoupleAssetLimit()));
                boolean assetApplicable = loginMember.getPosition().equals(Position.NEWLY_MARRIED_COUPLE) && loginMember.getAssetPrice() <= post.getNewlyMarriedCoupleAssetLimit();
                dto.add(ApplicationConditionDto.of(assetContent, assetApplicable));
            }
        }

        return dto;
    }

    public List<ApplicationConditionDto> makePositionConditionDtos(Post post, House house, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        postHouseService.getSupplyTypesByPostIdAndHouseHid(post.getId(), house.getId())
                .forEach(position -> dto.add(ApplicationConditionDto.of(
                        position.getName(),
                        loginMember.getPosition().equals(position)
                )));

        return dto;
    }

    public PaginationDto<PostSummaryResponse> fetchHomePosts(Integer page, Integer size, String loginMemberId) {
        Member loginMember = memberService.getMemberById(loginMemberId);
        Page<Post> posts = postRepository.findAll(PageRequest.of(page - 1, size));

        List<PostSummaryResponse> postSummaryResponses = new ArrayList<>();
        // 공고별 aggregation
        for (Post post : posts) {
            // [ 1. 지원 조건 Dto ]
            List<ApplicationConditionDto> applicationConditionDtos = new ArrayList<>();
            // 1-1. 소득 조건
            applicationConditionDtos.addAll(makeIncomeConditionDto(post, loginMember));
            // 1-2. 자동차 가액 조건
            applicationConditionDtos.addAll(makeCarPriceConditionDto(post, loginMember));
            // 1-3. 자산 조건
            applicationConditionDtos.addAll(makeAssetConditionDtos(post, loginMember));


            // 주택별 aggregation
            List<HouseSummaryResponse> houseSummaryResponse = new ArrayList<>();
            List<String> houseIds = postHouseService.getHouseIdsByPostId(post.getId());
            for (String houseId : houseIds) {
                House house = houseService.getHouseById(houseId);

                // 1-4. 지원 자격 조건
                applicationConditionDtos.addAll(makePositionConditionDtos(post, house, loginMember));


                houseSummaryResponse.add(HouseSummaryResponse.of(
                        house.getId(),
                        houseImageService.getThumbnailUrl(houseId),
                        house.getName(),
                        getSubscriptionState(post.getId()),
                        applicationConditionDtos,
                        postHouseService.getRentDtosByPostIdAndHouseId(post.getId(), houseId),
                        house.getDistrict().getName(),
                        house.getLatitude(),
                        house.getLongitude()
                ));
            }

            postSummaryResponses.add(
                    PostSummaryResponse.of(
                            post.getId(),
                            post.getTitle(),
                            post.getBadges().stream().map(Badge::getName).toList(),
                            houseSummaryResponse,
                            post.getSchedules().stream()
                                    .sorted(Comparator.comparing(PostSchedule::getOrdering))
                                    .map(schedule -> ScheduleDto.of(
                                            schedule.getId(),
                                            schedule.getTitle(),
                                            schedule.getStartDateTime() != null ? schedule.getStartDateTime().toString() : schedule.getStartNote(),
                                            schedule.getEndDateTime() != null ? schedule.getEndDateTime().toString() : schedule.getEndNote(),
                                            postScheduleNotificationRepository.existsByPostSchedule_IdAndMember_Id(schedule.getId(), loginMemberId)
                                    ))
                                    .toList()
                    )
            );
        }

        return PaginationDto.of(
                posts.getTotalPages(),
                postSummaryResponses
        );
    }
}
