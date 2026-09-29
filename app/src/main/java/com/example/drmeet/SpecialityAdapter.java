package com.example.drmeet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {

    private final List<Speciality> specialities;
    private OnItemClickListener listener;

    public SpecialityAdapter(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_speciality, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Speciality speciality = specialities.get(position);
        holder.specialityImage.setImageResource(speciality.getImageResId());
        holder.specialityName.setText(speciality.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(speciality);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView specialityImage;
        TextView specialityName;

        ViewHolder(View itemView) {
            super(itemView);
            specialityImage = itemView.findViewById(R.id.speciality_icon);
            specialityName = itemView.findViewById(R.id.speciality_name);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Speciality speciality);
    }
}
