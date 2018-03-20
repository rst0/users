package com.example.users.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApiResourceConfig extends ResourceConfig
{
    public ApiResourceConfig()
    {
        register(ApiEndpointMapper.class);
    }
}
