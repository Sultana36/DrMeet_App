package com.example.drmeet;

public class Speciality {
    private final int imageResId;
    private final String name;

    public Speciality(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }
}

