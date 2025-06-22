package com.krowfeather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
    @RequestMapping("/fallback")
    public String fallback(){
        return "CircuitBreaker熔断";
    }

    @RequestMapping("/fallbackRateLimiter")
    public String fallbackRateLimiter(){
        return "RateLimiter限流";
    }
}
