package com.raoni.agentorchestrator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Spring AI Agent Orchestrator")
                .version("0.1.0")
                .description("API-first agent orchestration with tool calling, policy checks, and execution traces."));
    }
}
