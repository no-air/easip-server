package com.noair.easip.house.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import com.noair.easip.member.domain.Member;
import com.noair.easip.util.DeletableBaseEntity;

@Entity
@Table(name = "DISTRICT")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class District extends DeletableBaseEntity {
    @Id
    @Column(name = "DISTRICT_ID", columnDefinition = "CHAR(26)")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "districts", fetch = FetchType.LAZY)
    @Builder.Default
    private List<House> houses = new ArrayList<>();

    @ManyToMany(mappedBy = "likingDistricts", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Member> members = new ArrayList<>();
} 