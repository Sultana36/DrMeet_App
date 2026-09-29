package com.example.drmeet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.drmeet.Database.DatabaseHelper;

public class PatientInformationListAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;

    public PatientInformationListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_INFORMATION_ID));
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
            holder = new ViewHolder();

            holder.nameTextView = convertView.findViewById(R.id.tv_name);
            holder.emailTextView = convertView.findViewById(R.id.tv_email);
            holder.phoneTextView = convertView.findViewById(R.id.tv_phone);
            holder.appointmentTypeTextView = convertView.findViewById(R.id.tv_appointment_type);
            holder.appointmentTimeTextView = convertView.findViewById(R.id.tv_appointment_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Move cursor to the correct position
        if (cursor != null && cursor.moveToPosition(position)) {
            holder.nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_INFORMATION_NAME)));
            holder.emailTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_INFORMATION_EMAIL)));
            holder.phoneTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_INFORMATION_PHONE)));
            holder.appointmentTypeTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_APPOIMENT_TYPE)));
            holder.appointmentTimeTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PATIENT_APPOIMENT_TIME)));
        }

        return convertView;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;
        TextView appointmentTypeTextView;
        TextView appointmentTimeTextView;
    }
}
