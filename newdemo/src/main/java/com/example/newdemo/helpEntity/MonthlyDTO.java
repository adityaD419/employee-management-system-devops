package com.example.newdemo.helpEntity;

public class MonthlyDTO {
    private String month;
    private Double rate;

    public MonthlyDTO(String month, Double rate) {
        this.month = month;
        this.rate = rate;
    }

    // Getters and setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
