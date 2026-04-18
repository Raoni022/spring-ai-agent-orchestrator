package com.raoni.agentorchestrator.tools.application;

import com.raoni.agentorchestrator.tools.domain.AgentTool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ToolRegistry {
    private final Map<String, AgentTool> tools;

    public ToolRegistry(List<AgentTool> tools) {
        this.tools = tools.stream().collect(Collectors.toMap(AgentTool::name, Function.identity()));
    }

    public Optional<AgentTool> find(String name) {
        return Optional.ofNullable(tools.get(name));
    }

    public List<ToolDescriptor> listTools() {
        return tools.values().stream()
                .map(tool -> new ToolDescriptor(tool.name(), tool.description()))
                .sorted((a, b) -> a.name().compareTo(b.name()))
                .toList();
    }

    public record ToolDescriptor(String name, String description) {}
}
