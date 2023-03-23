package com.example.hom;

public class StudentModel {


    String Fristname;
    String Lastname;
    String image;

    public StudentModel(){

    }

    public StudentModel(String fristname, String lastname, String image) {
        Fristname = fristname;
        Lastname = lastname;
        this.image = image;
    }

    public String getFristname() {
        return Fristname;
    }

    public void setFristname(String fristname) {
        Fristname = fristname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
