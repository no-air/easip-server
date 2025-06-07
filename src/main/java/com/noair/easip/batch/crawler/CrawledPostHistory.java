package com.noair.easip.batch.crawler;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class CrawledPostHistory {
    @Id
    private String boardId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(2083)")
    private String postFileUrl;

    private String postFileName;

    @Column(nullable = false, columnDefinition = "CHAR(26)")
    private String insertPostId;

    @Column(nullable = false, columnDefinition = "CHAR(26)")
    private String updateHouseId;

    @Column(nullable = false)
    private LocalDateTime crawlingDateTime;
}
