package com.noair.easip.post.service;

import com.noair.easip.house.controller.dto.HouseSummaryResponse;
import com.noair.easip.house.controller.dto.RentDto;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.house.domain.House;
import com.noair.easip.house.service.HouseImageService;
import com.noair.easip.house.service.HouseService;
import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.Position;
import com.noair.easip.member.service.MemberService;
import com.noair.easip.post.controller.dto.*;
import com.noair.easip.post.domain.Post;
import com.noair.easip.post.exception.PostNotFoundException;
import com.noair.easip.post.repository.PostRepository;
import com.noair.easip.util.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.noair.easip.util.KoreanStringConvertor.toPriceString;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHouseService postHouseService;
    private final PostScheduleService postScheduleService;
    private final HouseService houseService;
    private final HouseImageService houseImageService;
    private final MemberService memberService;

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public PaginationDto<PostSummaryResponse> fetchHomePosts(Integer page, Integer size, String loginMemberId) {
        Member loginMember = memberService.getMemberById(loginMemberId);
        List<PostSummaryResponse> postSummaryResponses = new ArrayList<>();

        Page<Post> posts = postRepository.searchHomePosts(PageRequest.of(page - 1, size));
        for (Post post : posts) {

            List<ScheduleDto> scheduleDtos = postScheduleService.getScheduleDtoByPost(post, loginMemberId);
            List<House> houses = houseService.getHousesByPostId(post.getId());
            List<HouseSummaryResponse> houseSummaryResponse = makeHouseSummaryResponse(post, houses, loginMember);

            // [ 공고 요약 Dto ]
            postSummaryResponses.add(
                    PostSummaryResponse.of(
                            post.getId(),
                            post.getTitle(),
                            post.getBadges().stream().map(Badge::getName).toList(),
                            houseSummaryResponse,
                            scheduleDtos
                    )
            );
        }

        return PaginationDto.of(
                posts.getTotalPages(),
                postSummaryResponses
        );
    }

    public List<HouseSummaryResponse> makeHouseSummaryResponse(Post post, List<House> houses, Member loginMember) {
        return houses.stream().map(house -> {
            List<ApplicationConditionDto> applicationConditionDtos = getApplicationConditionDtos(loginMember, post, house);
            List<RentDto> rentDtos = postHouseService.getRentDtosByPostIdAndHouseId(post.getId(), house.getId());
            String thumbnailUrl = houseImageService.getThumbnailImageUrl(house.getId());
            String subscriptionState = postScheduleService.getSubscriptionStateKorNameByPostId(post.getId());

            return HouseSummaryResponse.of(
                    house.getId(),
                    thumbnailUrl,
                    house.getName(),
                    subscriptionState,
                    applicationConditionDtos,
                    rentDtos,
                    house.getDistrict().getName(),
                    house.getLatitude(),
                    house.getLongitude()
            );
        }).toList();
    }

    public PaginationDto<PostElementResponse> fetchPostList(String keyword, String loginMemberId, Integer page, Integer size) {
        Page<Post> posts = postRepository.findAllByTitleContainingIgnoreCase(keyword, PageRequest.of(page - 1, size));
        return makePostElementResponsePaginationDto(loginMemberId, posts);
    }

    public PaginationDto<PostElementResponse> fetchLikingPostList(String keyword, String loginMemberId, Integer page, Integer size) {
        Page<Post> posts = postRepository.searchLikingPosts(keyword, loginMemberId, PageRequest.of(page - 1, size));
        return makePostElementResponsePaginationDto(loginMemberId, posts);
    }

    public PaginationDto<PostElementResponse> makePostElementResponsePaginationDto(String loginMemberId, Page<Post> posts) {
        List<PostElementResponse> postElementResponses = posts.stream().map(
                post -> PostElementResponse.of(
                        post.getId(),
                        houseService.getHouseThumbnailUrlByPostId(post.getId()),
                        post.getTitle(),
                        postScheduleService.getSubscriptionStateKorNameByPostId(post.getId()),
                        postScheduleService.getApplicationStartStringByPostId(post.getId()),
                        postScheduleService.getApplicationEndStringByPostId(post.getId()),
                        postHouseService.getNumberOfUnitsRecruitingByPostIdAndHouseId(post.getId(), null), // 공고에 포함된 모든 주택의 공급호수
                        postScheduleService.isPushAlarmRegistered(post.getId(), loginMemberId)
                )
        ).toList();

        return PaginationDto.of(
                posts.getTotalPages(),
                postElementResponses
        );
    }

    public List<PostPerHouseDetailDto> getPostPerHouseDetails(String postId, String loginMemberId) {
        List<PostPerHouseDetailDto> postPerHouseDetailDtos = new ArrayList<>();
        Member loginMember = memberService.getMemberById(loginMemberId);
        Post post = getPostById(postId);
        List<House> houses = houseService.getHousesByPostId(postId);

        List<ScheduleDto> scheduleDtos = postScheduleService.getScheduleDtoByPost(post, loginMemberId);
        for (House house : houses) {
            List<ApplicationConditionDto> applicationConditionDtos = getApplicationConditionDtos(loginMember, post, house);
            List<PostHouseConditionDto> postHouseConditions = postHouseService.getPostHouseConditionDtos(postId, house.getId());

            postPerHouseDetailDtos.add(PostPerHouseDetailDto.of(
                    house.getId(),
                    house.getName(),
                    houseImageService.getThumbnailImageUrl(house.getId()),
                    house.getRoadAddress(),
                    postHouseConditions.getFirst().minRatioDeposit(),
                    postHouseConditions.getFirst().minRatioMonthlyRent(),
                    postHouseService.getNumberOfUnitsRecruitingByPostIdAndHouseId(postId, house.getId()),
                    applicationConditionDtos,
                    scheduleDtos,
                    postHouseConditions,
                    house.getPageUrl()
            ));
        }

        return postPerHouseDetailDtos;
    }

    public List<ApplicationConditionDto> getApplicationConditionDtos(Member loginMember, Post post, House house) {
        return Stream.of(
                makeIncomeConditionDto(post, loginMember),
                makeCarPriceConditionDto(post, loginMember),
                makeAssetConditionDtos(post, loginMember),
                makePositionConditionDtos(post, house, loginMember)
        ).flatMap(List::stream).toList();
    }

    public List<ApplicationConditionDto> makeIncomeConditionDto(Post post, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        if (Boolean.TRUE.equals(post.getIsIncomeLimited())) {
            String content = String.format("소득기준 %s원 이내(1인가구 기준)", toPriceString(post.getIncomeLimit1Person()));
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
            String carPriceContent = String.format("자동차가액 %s원 이내", toPriceString(post.getCarPriceLimit()));
            boolean carPriceApplicable = loginMember.getCarPrice() <= post.getCarPriceLimit();
            dto.add(ApplicationConditionDto.of(carPriceContent, carPriceApplicable));
        }

        return dto;
    }

    public List<ApplicationConditionDto> makeAssetConditionDtos(Post post, Member loginMember) {
        List<ApplicationConditionDto> dto = new ArrayList<>();

        if (Boolean.TRUE.equals(post.getIsAssetLimited())) {
            if (post.getYoungManAssetLimit() != null) {
                String assetContent = String.format("청년 자산기준 %s원 이내", toPriceString(post.getYoungManAssetLimit()));
                boolean assetApplicable = loginMember.getPosition().equals(Position.YOUNG_MAN) && loginMember.getAssetPrice() <= post.getYoungManAssetLimit();
                dto.add(ApplicationConditionDto.of(assetContent, assetApplicable));
            }
            if (post.getNewlyMarriedCoupleAssetLimit() != null) {
                String assetContent = String.format("신혼부부 자산기준 %s원 이내", toPriceString(post.getNewlyMarriedCoupleAssetLimit()));
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
