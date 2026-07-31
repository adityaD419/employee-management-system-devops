package com.example.newdemo.helpEntity;

import java.time.LocalDate;

public class RateDTO {
    private Double rate;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTill;
    private Long userId; // Added userId field

    // Getters and Setters

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTill() {
        return effectiveTill;
    }

    public void setEffectiveTill(LocalDate effectiveTill) {
        this.effectiveTill = effectiveTill;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
