package com.example.drmeet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopDoctorsAdapter extends RecyclerView.Adapter<TopDoctorsAdapter.ViewHolder> {

    private final List<TopDoctor> topDoctors;
    private OnItemClickListener listener;

    public TopDoctorsAdapter(List<TopDoctor> topDoctors) {
        this.topDoctors = topDoctors;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopDoctor topDoctor = topDoctors.get(position);
        holder.doctorImage.setImageResource(topDoctor.getImageResId());
        holder.doctorName.setText(topDoctor.getName());
        holder.doctorSpeciality.setText(topDoctor.getSpeciality());
        holder.doctorRating.setText(String.valueOf(topDoctor.getRating()));
        holder.doctorExperience.setText(String.format("%d years", topDoctor.getExperience()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(topDoctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topDoctors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorImage;
        TextView doctorName, doctorSpeciality, doctorExperience, doctorRating;

        ViewHolder(View itemView) {
            super(itemView);
            doctorImage = itemView.findViewById(R.id.doctor_image);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorSpeciality = itemView.findViewById(R.id.doctor_speciality);
            doctorRating = itemView.findViewById(R.id.doctor_rating);
            doctorExperience = itemView.findViewById(R.id.doctor_experience);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TopDoctor topDoctor);
    }
}
