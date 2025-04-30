package com.noair.subsapp.application;

import com.noair.rdb.subscription.core.foo.FooJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractDataService {
    private final FooJpaRepository fooJpaRepository;

    public void foo() {

    }
}
