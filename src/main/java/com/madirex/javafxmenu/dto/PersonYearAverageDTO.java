package com.madirex.javafxmenu.dto;

public class PersonYearAverageDTO {
    private double youngAvg;
    private double adultAvg;

    public PersonYearAverageDTO(double youngAvg, double adultAvg) {
        this.youngAvg = youngAvg;
        this.adultAvg = adultAvg;
    }

    public double getYoungAvg() {
        return youngAvg;
    }

    public double getAdultAvg() {
        return adultAvg;
    }
}
