package com.noair.subsapp.application;

import com.noair.client.subdata.ChatgptSubscriptionDataExtractor;
import org.springframework.stereotype.Service;

@Service
public class ExtractDataService {
    private final SubscriptionDataExtractor extractor = new ChatgptSubscriptionDataExtractor();

    public void foo() {

    }
}
