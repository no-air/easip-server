package com.noair.easip.post.domain;

import java.util.ArrayList;
import java.util.List;
import com.noair.easip.house.domain.Badge;
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
    @Column(name = "POST_ID")
    private String id;

    @Column(name = "TITLE", nullable = false)
    private String title;

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
} 