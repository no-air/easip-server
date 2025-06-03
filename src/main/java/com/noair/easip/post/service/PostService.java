package com.noair.easip.post.service;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.house.domain.House;
import com.noair.easip.house.service.HouseImageService;
import com.noair.easip.house.service.HouseService;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.Position;
import com.noair.easip.member.repository.PostScheduleNotificationRepository;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.post.controller.dto.ApplicationConditionDto;
import com.noair.easip.post.controller.dto.PostElementResponse;
import com.noair.easip.post.controller.dto.PostSummaryResponse;
import com.noair.easip.post.controller.dto.ScheduleDto;
import com.noair.easip.post.domain.Post;
import com.noair.easip.post.domain.PostSchedule;
import com.noair.easip.post.domain.SUBSCRIPTION_STATE;
import com.noair.easip.post.exception.PostNotFoundException;
import com.noair.easip.post.repository.PostRepository;
import com.noair.easip.post.repository.PostScheduleRepository;
import com.noair.easip.util.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.noair.easip.util.PriceStringConvertor.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHouseService postHouseService;
    private final PostScheduleService postScheduleService;
    private final PostScheduleNotificationRepository postScheduleNotificationRepository;
    private final HouseService houseService;
    private final HouseImageService houseImageService;
    private final MemberService memberService;

    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public PaginationDto<PostSummaryResponse> fetchHomePosts(Integer page, Integer size, String loginMemberId) {
        Member loginMember = memberService.getMemberById(loginMemberId);
        Page<Post> posts = postRepository.findAll(PageRequest.of(page - 1, size));

        List<PostSummaryResponse> postSummaryResponses = new ArrayList<>();
        // 공고별 aggregation
        for (Post post : posts) {
            // [ 지원 조건 Dto ]
            List<ApplicationConditionDto> applicationConditionDtos = new ArrayList<>();
            applicationConditionDtos.addAll(makeIncomeConditionDto(post, loginMember));
            applicationConditionDtos.addAll(makeCarPriceConditionDto(post, loginMember));
            applicationConditionDtos.addAll(makeAssetConditionDtos(post, loginMember));


            // 주택별 aggregation
            List<HouseSummaryResponse> houseSummaryResponse = new ArrayList<>();
            List<String> houseIds = postHouseService.getHouseIdsByPostId(post.getId());
            for (String houseId : houseIds) {
                House house = houseService.getHouseById(houseId);

                applicationConditionDtos.addAll(makePositionConditionDtos(post, house, loginMember));

                // [ 주택 요약 Dto ]
                houseSummaryResponse.add(HouseSummaryResponse.of(
                        house.getId(),
                        houseImageService.getThumbnailUrl(houseId),
                        house.getName(),
                        postScheduleService.getSubscriptionStateKorName(post.getId()),
                        applicationConditionDtos,
                        // [ 집세 Dto ]
                        postHouseService.getRentDtosByPostIdAndHouseId(post.getId(), houseId),
                        house.getDistrict().getName(),
                        house.getLatitude(),
                        house.getLongitude()
                ));
            }

            // [ 공고 요약 Dto ]
            postSummaryResponses.add(
                    PostSummaryResponse.of(
                            post.getId(),
                            post.getTitle(),
                            post.getBadges().stream().map(Badge::getName).toList(),
                            houseSummaryResponse,
                            // [ 공급 일정 Dto ]
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

    public PaginationDto<PostElementResponse> fetchPostList(Integer page, Integer size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page - 1, size));

        List<PostElementResponse> postElementResponses = posts.stream().map(
                post -> PostElementResponse.of(
                        post.getId(),
                        post.getTitle(),
                        postScheduleService.getApplicationStartStringByPostId(post.getId()),
                        postScheduleService.getApplicationEndStringByPostId(post.getId()),
                        postHouseService.getNumberOfUnitsRecruitingByPostIdAndHouseId(post.getId(), null) // 공고에 포함된 모든 주택의 공급호수
                )
        ).toList();

        return PaginationDto.of(
                posts.getTotalPages(),
                postElementResponses
        );
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
                        position.getKorName(),
                        loginMember.getPosition().equals(position)
                )));

        return dto;
    }
}
