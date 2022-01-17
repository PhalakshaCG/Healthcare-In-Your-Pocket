package com.frosoft.mavenproject1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Doctor {
    String Name,Specialization,Contact;
    int Fee;
    Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Doctor(String name, String specialization, String contact, int fee, Image image) {
        Name = name;
        Specialization = specialization;
        Contact = contact;
        Fee = fee;
        this.image = image;
    }

    public Doctor(String name, String specialization, int fee, Image image) {
        Name = name;
        Specialization = specialization;
        Fee = fee;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public int getFee() {
        return Fee;
    }

    public void setFee(int fee) {
        Fee = fee;
    }
}
