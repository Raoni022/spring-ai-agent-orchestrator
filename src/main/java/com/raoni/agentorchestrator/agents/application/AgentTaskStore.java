package com.raoni.agentorchestrator.agents.application;

import com.raoni.agentorchestrator.agents.dto.AgentTaskResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AgentTaskStore {
    private final Map<UUID, AgentTaskResponse> tasks = new ConcurrentHashMap<>();

    public void save(AgentTaskResponse response) {
        tasks.put(response.taskId(), response);
    }

    public Optional<AgentTaskResponse> find(UUID taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }
}
