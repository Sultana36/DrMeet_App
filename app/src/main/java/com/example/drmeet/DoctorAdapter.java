package com.example.drmeet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class DoctorAdapter extends BaseAdapter {
    private Context context;
    private List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return doctorList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.doctor_image);
        TextView nameTextView = convertView.findViewById(R.id.doctor_name);
        TextView professionTextView = convertView.findViewById(R.id.doctor_speciality);
        RatingBar ratingBar = convertView.findViewById(R.id.doctor_rating);
        Button appointmentButton = convertView.findViewById(R.id.appointment_button); // Add this

        Doctor doctor = doctorList.get(position);

        nameTextView.setText(doctor.getName());
        professionTextView.setText(doctor.getSpeciality());
        ratingBar.setRating(doctor.getRating());

        byte[] imageBytes = doctor.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.default_image);
        }

        // 👉 Set OnClickListener for the "Make Appointment" button
        appointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ScheduleActivity.class);
            intent.putExtra("DOCTOR_NAME", doctor.getName()); // Pass doctor info if needed
            intent.putExtra("DOCTOR_SPECIALITY", doctor.getSpeciality());
            context.startActivity(intent);
        });

        return convertView;
    }
}
