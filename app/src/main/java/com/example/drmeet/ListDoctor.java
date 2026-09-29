package com.example.drmeet;

public class ListDoctor {
    private final int doctorImageResId;
    private final String doctorName;
    private final String doctorSpeciality;
    private final double doctorRating;

    public ListDoctor(int doctorImageResId, String doctorName, String doctorSpeciality, double doctorRating) {
        this.doctorImageResId = doctorImageResId;
        this.doctorName = doctorName;
        this.doctorSpeciality = doctorSpeciality;
        this.doctorRating = doctorRating;
    }

    public int getDoctorImageResId() {
        return doctorImageResId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public double getDoctorRating() {
        return doctorRating;
    }
}
