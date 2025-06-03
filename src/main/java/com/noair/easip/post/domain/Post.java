package com.noair.easip.post.domain;

import java.util.ArrayList;
import java.util.List;
import com.noair.easip.house.domain.Badge;
import com.noair.easip.post.exception.IncomeLimitPersonExceedException;
import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class Post extends DeletableBaseEntity {
    @Id
    @Column(name = "POST_ID", columnDefinition = "CHAR(26)")
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean isIncomeLimited;
    private Double incomeLimit1Person;
    private Double incomeLimit2Person;
    private Double incomeLimit3Person;
    private Double incomeLimit4Person;
    private Double incomeLimit5Person;

    @Column(nullable = false)
    private Boolean isCarPriceLimited;
    private Double carPriceLimit;

    @Column(nullable = false)
    private Boolean isAssetLimited;
    private Double youngManAssetLimit;
    private Double newlyMarriedCoupleAssetLimit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "POST_BADGE",
        joinColumns = @JoinColumn(name = "POST_ID"),
        inverseJoinColumns = @JoinColumn(name = "BADGE_ID")
    )
    @Builder.Default
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostSchedule> schedules = new ArrayList<>();

    public double getIncomeLimit(int familyMemberCount) {
        return switch (familyMemberCount) {
            case 1 -> incomeLimit1Person;
            case 2 -> incomeLimit2Person;
            case 3 -> incomeLimit3Person;
            case 4 -> incomeLimit4Person;
            case 5 -> incomeLimit5Person;
            default -> throw new IncomeLimitPersonExceedException();
        };
    }
} 
