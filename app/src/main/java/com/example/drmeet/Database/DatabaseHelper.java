package com.example.drmeet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drmeet.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DrMeet.db";
    private static final int DATABASE_VERSION = 3;

    // Bookings and booked slots expire after 24 hours
    private static final long BOOKING_DURATION_MS = 24L * 60 * 60 * 1000;

    public static final String TABLE_DOCTORS = "doctors";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SPECIALITY = "speciality";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_IMAGE = "image";

    public static final String PATIENT_INFORMATION_TABLE = "patient_information";
    public static final String PATIENT_INFORMATION_ID = "id";
    public static final String PATIENT_INFORMATION_NAME = "name";
    public static final String PATIENT_INFORMATION_EMAIL = "email";
    public static final String PATIENT_INFORMATION_PHONE = "phone";
    public static final String PATIENT_APPOIMENT_TYPE = "appoimentType";
    public static final String PATIENT_APPOIMENT_TIME = "slotTime";

    // In this table the "slotTime" column now stores each button's unique key (e.g. "btn_morning4")
    public static final String SLOTE_TABLE = "time_table";

    // Time (in milliseconds) when the booking / slot was created
    public static final String COLUMN_BOOKED_AT = "booked_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDoctorTable(db);
        createBookingTables(db);
    }

    private void createDoctorTable(SQLiteDatabase db) {
        String CREATE_DOCTOR_TABLE = "CREATE TABLE " + TABLE_DOCTORS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SPECIALITY + " TEXT, "
                + COLUMN_RATING + " REAL, "
                + COLUMN_IMAGE + " BLOB)";
        db.execSQL(CREATE_DOCTOR_TABLE);
    }

    private void createBookingTables(SQLiteDatabase db) {
        String CREATE_PATIENT_INFORMATION_TABLE = "CREATE TABLE " + PATIENT_INFORMATION_TABLE + " ("
                + PATIENT_INFORMATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PATIENT_INFORMATION_NAME + " TEXT, "
                + PATIENT_INFORMATION_EMAIL + " TEXT, "
                + PATIENT_APPOIMENT_TYPE + " TEXT, "
                + PATIENT_APPOIMENT_TIME + " TEXT, "
                + PATIENT_INFORMATION_PHONE + " TEXT, "
                + COLUMN_BOOKED_AT + " INTEGER)";
        db.execSQL(CREATE_PATIENT_INFORMATION_TABLE);

        String CREATE_SLOTE_TABLE = "CREATE TABLE " + SLOTE_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PATIENT_APPOIMENT_TIME + " TEXT, "
                + COLUMN_BOOKED_AT + " INTEGER)";
        db.execSQL(CREATE_SLOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
            createDoctorTable(db);
        }
        if (oldVersion < 3) {
            // Doctors are kept. Only the booking tables are rebuilt with the new time column.
            db.execSQL("DROP TABLE IF EXISTS " + PATIENT_INFORMATION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SLOTE_TABLE);
            createBookingTables(db);
        }
    }

    // ---------------- Doctors ----------------

    public boolean insertDoctor(String name, String speciality, float rating, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SPECIALITY, speciality);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_IMAGE, image);

        long result = db.insert(TABLE_DOCTORS, null, values);
        return result != -1;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTORS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String speciality = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIALITY));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

                Doctor doctor = new Doctor(id, name, speciality, rating, image);
                doctorList.add(doctor);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return doctorList;
    }

    public Doctor getDoctorByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTORS, null, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String speciality = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIALITY));
            float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

            Doctor doctor = new Doctor(id, name, speciality, rating, image);
            cursor.close();
            return doctor;
        }
        return null;
    }

    public boolean deleteDoctor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_DOCTORS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean updateDoctor(int id, String name, String speciality, float rating, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SPECIALITY, speciality);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_IMAGE, image);

        int result = db.update(TABLE_DOCTORS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // ---------------- Bookings ----------------

    /** Removes patient records and booked slots that are older than 24 hours. */
    private void deleteExpiredBookings() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] cutoff = {String.valueOf(System.currentTimeMillis() - BOOKING_DURATION_MS)};
        db.delete(PATIENT_INFORMATION_TABLE, COLUMN_BOOKED_AT + " < ?", cutoff);
        db.delete(SLOTE_TABLE, COLUMN_BOOKED_AT + " < ?", cutoff);
    }

    public Cursor getAllPatient() {
        deleteExpiredBookings();
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + PATIENT_INFORMATION_TABLE, null);
    }

    /** @param slotKey the unique key of the slot button (e.g. "btn_morning4") */
    public boolean isTimeAvailable(String slotKey) {
        deleteExpiredBookings();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SLOTE_TABLE, null, PATIENT_APPOIMENT_TIME + " = ?", new String[]{slotKey}, null, null, null);
        boolean isAvailable = cursor.getCount() == 0;
        cursor.close();
        return isAvailable;
    }

    /**
     * Saves the patient AND locks the slot together, only when the form is submitted.
     *
     * @param displayTime text shown in the patient list (date + slot time)
     * @param slotKey     unique key of the slot button, used for availability checks
     * @return false if the slot was already taken or saving failed
     */
    public boolean bookAppointment(String name, String email, String phone, String appointmentType,
                                   String displayTime, String slotKey) {
        deleteExpiredBookings();
        SQLiteDatabase db = this.getWritableDatabase();
        long now = System.currentTimeMillis();

        db.beginTransaction();
        try {
            // Make sure nobody took the slot in the meantime
            Cursor cursor = db.query(SLOTE_TABLE, null, PATIENT_APPOIMENT_TIME + " = ?",
                    new String[]{slotKey}, null, null, null);
            boolean taken = cursor.getCount() > 0;
            cursor.close();
            if (taken) {
                return false;
            }

            ContentValues patient = new ContentValues();
            patient.put(PATIENT_INFORMATION_NAME, name);
            patient.put(PATIENT_INFORMATION_EMAIL, email);
            patient.put(PATIENT_INFORMATION_PHONE, phone);
            patient.put(PATIENT_APPOIMENT_TYPE, appointmentType);
            patient.put(PATIENT_APPOIMENT_TIME, displayTime);
            patient.put(COLUMN_BOOKED_AT, now);
            long patientResult = db.insert(PATIENT_INFORMATION_TABLE, null, patient);

            ContentValues slot = new ContentValues();
            slot.put(PATIENT_APPOIMENT_TIME, slotKey);
            slot.put(COLUMN_BOOKED_AT, now);
            long slotResult = db.insert(SLOTE_TABLE, null, slot);

            if (patientResult == -1 || slotResult == -1) {
                return false;
            }

            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }
}