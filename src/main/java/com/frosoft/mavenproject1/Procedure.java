package com.frosoft.mavenproject1;

public class Procedure {
    String Doctor,Procedure;
    int Cost;

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getProcedure() {
        return Procedure;
    }

    public void setProcedure(String procedure) {
        Procedure = procedure;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public Procedure(String doctor, String procedure, int cost) {
        Doctor = doctor;
        Procedure = procedure;
        Cost = cost;
    }
}
