package com.inventory.inventory_system.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.inventory.inventory_system.service.AlertService;

@Component
public class DailyAlertScheduler {

    private final AlertService alertService;

    public DailyAlertScheduler(AlertService alertService) {
        this.alertService = alertService;
    }

    // Every day at 6:00 AM
    @Scheduled(cron = "0 0 6 * * ?")
    public void runDailyAlerts() {
        alertService.checkAlerts();
    }
}
