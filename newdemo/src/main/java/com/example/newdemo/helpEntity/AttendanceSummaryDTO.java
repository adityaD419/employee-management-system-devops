package com.example.newdemo.helpEntity;

public class AttendanceSummaryDTO {
    private int fullDays;
    private int halfDays;
    private int individualHours; // Added this field
    private int totalHours;

    public AttendanceSummaryDTO(int fullDays, int halfDays, int individualHours, int totalHours) {
        this.fullDays = fullDays;
        this.halfDays = halfDays;
        this.individualHours = individualHours;
        this.totalHours = totalHours;
    }

    // Getters and Setters
    public int getFullDays() {
        return fullDays;
    }

    public void setFullDays(int fullDays) {
        this.fullDays = fullDays;
    }

    public int getHalfDays() {
        return halfDays;
    }

    public void setHalfDays(int halfDays) {
        this.halfDays = halfDays;
    }

    public int getIndividualHours() {
        return individualHours;
    }

    public void setIndividualHours(int individualHours) {
        this.individualHours = individualHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
}
