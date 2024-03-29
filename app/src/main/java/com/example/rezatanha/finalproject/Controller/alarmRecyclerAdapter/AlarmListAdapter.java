package com.example.rezatanha.finalproject.Controller.alarmRecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.R;

import java.util.Collections;
import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {
    private final OnItemClickListener listener;

    private final LayoutInflater mInflater;
    private List<Alarm> alarms = Collections.emptyList();

    public AlarmListAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_row_alarm, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.bind(alarms.get(position), listener);
        Alarm current = alarms.get(position);
        String hour = current.getHour()<10? "0"+current.getHour(): String.valueOf(current.getHour());
        String minute = current.getMinute()<10? "0"+current.getMinute(): String.valueOf(current.getMinute());
        String message = hour+ " : " + minute;
        holder.hour.setText(message);
    }

    public void setWords(List<Alarm> words) {
        alarms = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Alarm item);
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private final TextView hour;


        private AlarmViewHolder(View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.alarm_time_view_in_row);
        }

        void bind(Alarm alarm, AlarmListAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(alarm));
        }
    }
}
