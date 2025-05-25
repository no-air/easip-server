package com.noair.easip.house.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import com.noair.easip.post.domain.Post;
import com.noair.easip.util.DeletableBaseEntity;

@Entity
@Table(name = "BADGE")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class Badge extends DeletableBaseEntity {
    @Id
    @Column(name = "BADGE_ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "badges", fetch = FetchType.LAZY)
    @Builder.Default
    private List<House> houses = new ArrayList<>();

    @ManyToMany(mappedBy = "badges", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();
} 