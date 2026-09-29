package com.example.drmeet;

public class Doctor {
    private int id;
    private String name;
    private String speciality;
    private float rating;
    private byte[] image;

    public Doctor(int id, String name, String speciality, float rating, byte[] image) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public float getRating() {
        return rating;
    }

    public byte[] getImage() {
        return image;
    }
}
