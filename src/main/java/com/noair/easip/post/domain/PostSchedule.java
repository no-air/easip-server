package com.noair.easip.post.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.noair.easip.member.domain.PostScheduleNotification;
import com.noair.easip.util.DeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_SCHEDULE", indexes = {
    @Index(name = "IDX_POST_SCHEDULE_START_DATE", columnList = "START_DATE")
})
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class PostSchedule extends DeletableBaseEntity {
    @Id
    @Column(name = "POST_SCHEDULE_ID", columnDefinition = "CHAR(26)")
    private String id;

    @Column(name = "ORDERING", nullable = false)
    private Integer ordering;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "START_DATE_TIME")
    private LocalDateTime startDateTime;

    @Column(name = "START_NOTE")
    private String startNote;

    @Column(name = "END_DATE_TIME")
    private LocalDateTime endDateTime;

    @Column(name = "END_NOTE")
    private String endNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "postSchedule")
    @Builder.Default
    private List<PostScheduleNotification> notifications = new ArrayList<>();
} 
