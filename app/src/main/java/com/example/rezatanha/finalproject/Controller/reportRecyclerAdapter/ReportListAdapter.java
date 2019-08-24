package com.example.rezatanha.finalproject.Controller.reportRecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import java.util.Collections;
import java.util.List;

import saman.zamani.persiandate.PersianDate;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportViewHolder> {
    private Context ctxt;
    private final OnItemClickListener listener;

    private final LayoutInflater mInflater;
    private List<Report> reports = Collections.emptyList(); // Cached copy of words

    public ReportListAdapter(Context ctxt, OnItemClickListener listener1) {
        this.ctxt = ctxt;
        this.listener = listener1;
        mInflater = LayoutInflater.from(ctxt);
    }

    @NonNull
    @Override
    public ReportListAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_row_report, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportListAdapter.ReportViewHolder holder, int position) {

        holder.bind(reports.get(position), listener);
        Report current = reports.get(position);
        PersianDate pdate = new PersianDate(current.getDate().getTime());
        holder.brief.setText(current.getHeader());
    }

    public void setReports(List<Report> words) {
        reports = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView brief;

        public ReportViewHolder(View itemView) {
            super(itemView);
            brief = itemView.findViewById(R.id.report_show_brief);
        }

        public void bind(Report alarm, ReportListAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(alarm));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Report item);
    }
}
