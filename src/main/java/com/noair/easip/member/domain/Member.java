package com.noair.easip.member.domain;

import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.noair.easip.house.domain.District;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class Member extends DeletableBaseEntity {

    @Id
    @Column(length = 26, columnDefinition = "CHAR(26)")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIVING_DISTRICT_ID", nullable = false)
    private District livingDistrict;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "MEMBER_DISTRICT",
        joinColumns = @JoinColumn(name = "MEMBER_ID"),
        inverseJoinColumns = @JoinColumn(name = "DISTRICT_ID")
    )
    @Builder.Default
    private List<District> likingDistricts = new ArrayList<>();

    @Column(nullable = false)
    private Integer allFamilyMemberCount;
    
    @Column(nullable = false)
    @Builder.Default
    private Double myMonthlySalary = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double familyMemberMonthlySalary = 0.0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private Boolean hasCar;

    @Builder.Default
    private Long carPrice = 0L;

    @Builder.Default
    private Double assetPrice = 0.0;

    public void updateProfile(
            String name,
            LocalDate dateOfBirth,
            District livingDistrict,
            List<District> likingDistricts,
            Integer allFamilyMemberCount,
            Double myMonthlySalary,
            Double familyMemberMonthlySalary,
            Position position,
            Boolean hasCar,
            Long carPrice,
            Double assetPrice
    ) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.livingDistrict = livingDistrict;
        this.likingDistricts = likingDistricts;
        this.allFamilyMemberCount = allFamilyMemberCount;
        this.myMonthlySalary = myMonthlySalary;
        this.familyMemberMonthlySalary = familyMemberMonthlySalary;
        this.position = position;
        this.hasCar = hasCar;
        this.carPrice = carPrice;
        this.assetPrice = assetPrice;
    }
}
