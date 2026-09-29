package com.example.drmeet;

public class TopDoctor {
    private final int imageResId;
    private final String name;
    private final String speciality;
    private final double rating;
    private final int experience;

    public TopDoctor(int imageResId, String name, String speciality, double rating, int experience) {
        this.imageResId = imageResId;
        this.name = name;
        this.speciality = speciality;
        this.rating = rating;
        this.experience = experience;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public double getRating() {
        return rating;
    }

    public int getExperience() {
        return experience;
    }
}

