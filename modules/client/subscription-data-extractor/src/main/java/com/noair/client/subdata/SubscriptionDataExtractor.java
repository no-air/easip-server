package com.noair.client.subdata;

import com.noair.client.subdata.dto.SubscriptionData;

import java.util.List;

public interface SubscriptionDataExtractor {
    List<SubscriptionData> extractor(String targetDate);
}
