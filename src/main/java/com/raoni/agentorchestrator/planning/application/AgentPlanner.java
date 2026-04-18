package com.raoni.agentorchestrator.planning.application;

import com.raoni.agentorchestrator.agents.dto.AgentTaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentPlanner {
    public List<String> plan(AgentTaskRequest request) {
        return request.allowedTools().stream()
                .filter(tool -> tool.equals("crm.lookup") || tool.equals("calendar.propose_slots") || tool.equals("notification.draft_email"))
                .limit(5)
                .toList();
    }
}
