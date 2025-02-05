package com.app.milkbook;

public class ReportModel {
    //private int customerId;
    private String name;
    private double fat, weight, perLitre, result;
    private String entryDate;

    public ReportModel(String name, double fat, double weight, double perLitre, double result, String entryDate) {
        //this.customerId = customerId;
        this.name = name;
        this.fat = fat;
        this.weight = weight;
        this.perLitre = perLitre;
        this.result = result;
        this.entryDate = entryDate;
    }

    // Getters for each field
    //public int getCustomerId() { return customerId; }
    public String getCustomerName() { return name; }
    public double getFat() { return fat; }
    public double getWeight() { return weight; }
    public double getPerLitre() { return perLitre; }
    public double getResult() { return result; }
    public String getEntryDate() { return entryDate; }
}
