package com.example.springbootactuator.entpoint;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "myendpoint", enableByDefault = true)
public class MyEndpoint {
    @ReadOperation
    public Map<String, Object> endpointMyRead(@Selector String content) {
        Map<String, Object> customMap = new HashMap<>();
        customMap.put("httpMethod", HttpMethod.GET.toString());
        customMap.put("status", "200");
        customMap.put("content", content);
        return customMap;
    }

    @WriteOperation
    public Map<String, Object> endpointMyWrite() {
        Map<String, Object> customMap = new HashMap<>();
        customMap.put("httpMethod", HttpMethod.POST.toString());
        return customMap;
    }

    @DeleteOperation
    public Map<String, Object> endpointMyDelete() {
        Map<String, Object> customMap = new HashMap<>();
        customMap.put("httpMethod", HttpMethod.DELETE.toString());
        return customMap;
    }
}
