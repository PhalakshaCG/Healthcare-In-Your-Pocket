package com.frosoft.mavenproject1;

public class Medicine {
    String Name,Dosage;
    int cost;

    public Medicine(String name, String dosage, int cost) {
        Name = name;
        Dosage = dosage;
        this.cost = cost;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
