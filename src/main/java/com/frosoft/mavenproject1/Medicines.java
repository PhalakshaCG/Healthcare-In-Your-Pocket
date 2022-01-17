/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;

/**
 *
 * @author Phalaksha C G
 */
public class Medicines {
    public int Quantity,Available;
    public String Name;

    public Medicines(String Name,int Quantity, int Available) {
        this.Quantity = Quantity;
        this.Available = Available;
        this.Name = Name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getAvailable() {
        return Available;
    }

    public String getName() {
        return Name;
    }
    
    
}
