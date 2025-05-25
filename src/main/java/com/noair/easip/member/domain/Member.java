package com.noair.easip.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import com.noair.easip.util.DeletableBaseEntity;

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
    private Double myMothlySalary;

    @Column(nullable = false)
    private Double familyMemberMonthlySalary;

    @Column(nullable = false)
    private Position position;

    @Column(nullable = false)
    private Boolean hasCar;

    private Long carPrice;

    private Double assetPrice;

}
