package com.noair.client.subdata;

import com.noair.client.subdata.dto.SubscriptionData;

import java.util.List;

public class ChatgptSubscriptionDataExtractor implements SubscriptionDataExtractor {
    @Override
    public List<SubscriptionData> extractor(String targetDate) {
        return List.of();
    }
}
