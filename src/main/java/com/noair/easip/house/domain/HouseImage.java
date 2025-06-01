package com.noair.easip.house.domain;

import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOUSE_IMAGE")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class HouseImage extends DeletableBaseEntity {
    @Id
    @Column(name = "HOUSE_IMAGE_ID", columnDefinition = "CHAR(26)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    private House house;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "HOUSE_IMAGE_TYPE", nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private HouseImageType houseImageType;

    @Column(name = "ORDERING", nullable = false)
    private Integer ordering;

} 
