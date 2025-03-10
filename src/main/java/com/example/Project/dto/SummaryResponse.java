package com.example.Project.dto;

public class SummaryResponse {
    private long totalAvailableProducts;
    private double totalAvailableValue;

    public SummaryResponse(long totalAvailableProducts, double totalAvailableValue) {
        this.totalAvailableProducts = totalAvailableProducts;
        this.totalAvailableValue = totalAvailableValue;
    }

    public long getTotalAvailableProducts() {
        return totalAvailableProducts;
    }

    public void setTotalAvailableProducts(long totalAvailableProducts) {
        this.totalAvailableProducts = totalAvailableProducts;
    }

    public double getTotalAvailableValue() {
        return totalAvailableValue;
    }

    public void setTotalAvailableValue(double totalAvailableValue) {
        this.totalAvailableValue = totalAvailableValue;
    }

}
