package com.noair.easip.batch.crawler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class PostCrawlingHistory {
    @Id
    private LocalDateTime crawlingDateTime;

    @Column(nullable = false)
    @Builder.Default
    private Integer readPostCnt = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer addPostCnt = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer updatePostCnt = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer deletePostCnt = 0;

    @Column(nullable = false)
    private Boolean isSuccess;

    @Column(columnDefinition = "VARCHAR(2083)")
    private String comment;

    public void incrementReadPostCnt() {
        this.readPostCnt++;
    }

    public void incrementAddPostCnt() {
        this.addPostCnt++;
    }

    public void incrementUpdatePostCnt() {
        this.updatePostCnt++;
    }

    public void incrementDeletePostCnt() {
        this.deletePostCnt++;
    }

    public void failedCrawl(String comment) {
        this.isSuccess = false;
        this.comment = comment;
    }
}
