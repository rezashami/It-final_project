package com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.WordViewHolder> {

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
        holder.name.setText(current.getName());
        Uri temp = Uri.parse(current.getImage());
//        Glide.with(ctxt)
//                .load(new File(temp.getPath()))
//                .into(holder.img);
        try {
            holder.img.setImageBitmap(MediaStore.Images.Media.getBitmap(ctxt.getContentResolver(), temp));
        } catch (IOException e) {
            e.printStackTrace();
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
