package com.raoni.agentorchestrator.audit.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    public void record(String eventType, String details) {
        log.info("audit eventType={} details={}", eventType, details);
    }
}
