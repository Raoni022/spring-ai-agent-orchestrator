package com.raoni.agentorchestrator.planning.application;

import com.raoni.agentorchestrator.agents.dto.AgentTaskRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AgentPlannerTest {
    private final AgentPlanner planner = new AgentPlanner();

    @Test
    void keepsOnlyRegisteredWorkflowTools() {
        var request = new AgentTaskRequest(
                "Qualify lead",
                "Lead wants automation",
                List.of("crm.lookup", "unknown.tool", "calendar.propose_slots"),
                false
        );

        assertThat(planner.plan(request)).containsExactly("crm.lookup", "calendar.propose_slots");
    }
}
