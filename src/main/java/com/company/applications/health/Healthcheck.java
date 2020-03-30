package com.company.applications.health;

import com.codahale.metrics.health.HealthCheck;

public class Healthcheck extends HealthCheck {
    @Override
    protected Result check() {
        return Result.healthy();
    }
}
