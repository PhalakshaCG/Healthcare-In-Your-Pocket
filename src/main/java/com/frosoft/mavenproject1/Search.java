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
public interface Search {
    boolean isfound = false;
    void search();
    static boolean searchEnabled(){
        return false;
    }
    
}
