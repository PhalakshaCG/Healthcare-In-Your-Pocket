package com.frosoft.mavenproject1;

public class Report {
    int SLNo;
    String Name;

    public Report(int SLNo, String name) {
        this.SLNo = SLNo;
        Name = name;
    }
    public void setSLNo(int SLNo) {
        this.SLNo = SLNo;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getSLNo() {
        return SLNo;
    }
    public String getName() {
        return Name;
    }
}
