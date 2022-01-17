package com.frosoft.mavenproject1;

public class Test {
    String Name;
    int Cost;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public Test(String name, int cost) {
        Name = name;
        Cost = cost;
    }
}
