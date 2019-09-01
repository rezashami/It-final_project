package com.example.rezatanha.finalproject.Controller.prescriptionRecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.R;

import java.util.Collections;
import java.util.List;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.PrescriptionViewHolder> {


    private final OnItemClickListener listener;

    private final LayoutInflater mInflater;
    private List<Prescription> prescriptions = Collections.emptyList();

    public PrescriptionListAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_row_prescription, parent, false);
        return new PrescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {
        holder.bind(prescriptions.get(position), listener);
        Prescription current = prescriptions.get(position);
        holder.textView.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Prescription item);
    }

    class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        void bind(Prescription prescription, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(prescription));
        }

        PrescriptionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.prescription_row_brief);
        }
    }
}
