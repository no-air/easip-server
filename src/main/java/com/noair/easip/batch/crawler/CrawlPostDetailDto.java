package com.noair.easip.batch.crawler;

public record CrawlPostDetailDto(
        String boardId,
        String title,
        String address,
        String compactAddress,
        String postFileBase64,
        String postFileName
) {
    public static CrawlPostDetailDto of(
            String boardId,
            String title,
            String address,
            String compactAddress,
            String postFileBase64,
            String postFileName
    ) {
        return new CrawlPostDetailDto(
                boardId,
                title,
                address,
                compactAddress,
                postFileBase64,
                postFileName
        );
    }
}
