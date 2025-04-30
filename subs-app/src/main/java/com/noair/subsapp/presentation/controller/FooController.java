package com.noair.subsapp.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @GetMapping("/")
    public String foo() {
        return "foo";
    }
}
