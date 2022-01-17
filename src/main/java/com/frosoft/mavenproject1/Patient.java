/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;

import javafx.scene.control.Button;

/**
 *
 * @author Phalaksha C G
 */
public class Patient {
    
    public int MRN_Number;
    public String Name,Contact;
    Button Report,Bill;
    public Patient(int MRN_Number, String Name, Button Report, String Contact,Button bill) {
        this.MRN_Number = MRN_Number;
        this.Name = Name;
        this.Report = Report;
        this.Contact = Contact;
        Bill=bill;
    }
    public Button getBill(){return Bill;}
    public int getMRN_Number() {
        return MRN_Number;
    }

    public String getName() {
        return Name;
    }

    public Button getReport() {
        return Report;
    }

    public String getContact() {
        return Contact;
    }

    public void setMRN_Number(int MRN_Number) {
        this.MRN_Number = MRN_Number;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setReport(Button Report) {
        this.Report = Report;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    
}
