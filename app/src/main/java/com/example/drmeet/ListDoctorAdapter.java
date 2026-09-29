package com.example.drmeet;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListDoctorAdapter extends RecyclerView.Adapter<ListDoctorAdapter.ViewHolder> {

    private final List<ListDoctor> doctorDataList;
    private final Context context;

    public ListDoctorAdapter(Context context, List<ListDoctor> doctorDataList) {
        this.context = context;
        this.doctorDataList = doctorDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListDoctor doctorData = doctorDataList.get(position);

        holder.doctorImage.setImageResource(doctorData.getDoctorImageResId());
        holder.doctorName.setText(doctorData.getDoctorName());
        holder.doctorSpeciality.setText(doctorData.getDoctorSpeciality());
        holder.doctorRating.setRating((float) doctorData.getDoctorRating());

        holder.appointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ScheduleActivity.class);
            intent.putExtra("DOCTOR_NAME", doctorData.getDoctorName());
            intent.putExtra("DOCTOR_SPECIALITY", doctorData.getDoctorSpeciality());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorImage;
        TextView doctorName, doctorSpeciality;
        RatingBar doctorRating;
        Button appointmentButton;

        ViewHolder(View itemView) {
            super(itemView);
            doctorImage = itemView.findViewById(R.id.doctor_image);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorSpeciality = itemView.findViewById(R.id.doctor_speciality);
            doctorRating = itemView.findViewById(R.id.doctor_rating);
            appointmentButton = itemView.findViewById(R.id.appointment_button);
        }
    }
}