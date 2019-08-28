package com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.WordViewHolder> {

    private static final String TAG = MedicineListAdapter.class.getSimpleName();

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView img;

        private WordViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.medicine_row_name);
            img = itemView.findViewById(R.id.medicine_row_image);
        }

        public void bind(Medicine medicine, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(medicine));
        }
    }

    private Context ctxt;
    private final OnItemClickListener listener;

    private final LayoutInflater mInflater;
    private List<Medicine> mWords = Collections.emptyList(); // Cached copy of words

    public MedicineListAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        ctxt = context;
    }

    @NonNull
    @Override
    public MedicineListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_row_medicine, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.bind(mWords.get(position), listener);
        Medicine current = mWords.get(position);
        Log.e(TAG, current.getString());
        holder.name.setText(current.getName());
        if (current.getImage() != null) {
            Glide.with(ctxt)
                    .load(Uri.fromFile(new File(current.getImage())))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.default_image_64)
                    .into(holder.img);
        } else {
            Glide.with(ctxt)
                    .load(R.drawable.default_image_64)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.img);
        }

    }

    public void setMedicines(List<Medicine> medicines) {
        mWords = medicines;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Medicine item);
    }

}
