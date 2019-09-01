package com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Medicine.SimpleMedicineModel;
import com.example.rezatanha.finalproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowMedicineListAdapter extends RecyclerView.Adapter<ShowMedicineListAdapter.WordViewHolder> {
    private List<Medicine> mWords = Collections.emptyList(); // Cached copy of words
    private final LayoutInflater mInflater;
    private List<SimpleMedicineModel> simpleMedicineModels = new ArrayList<>();


    public ShowMedicineListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShowMedicineListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_show_simplify_drug, parent, false);
        return new ShowMedicineListAdapter.WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowMedicineListAdapter.WordViewHolder holder, int position) {
        final SimpleMedicineModel simpleMedicineModel = simpleMedicineModels.get(position);
        Medicine current = mWords.get(position);
        holder.name.setText(current.getName());
        holder.view.setBackgroundColor(simpleMedicineModel.isSelected() ? Color.CYAN : Color.WHITE);
        holder.name.setOnClickListener(view -> {
            simpleMedicineModel.setSelected(!simpleMedicineModel.isSelected());
            holder.view.setBackgroundColor(simpleMedicineModel.isSelected() ? Color.CYAN : Color.WHITE);
        });

    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void setWords(List<Medicine> words) {
        mWords = words;

        for (int i = 0; i < mWords.size(); i++) {
            simpleMedicineModels.add(new SimpleMedicineModel(mWords.get(i).getName()));
            simpleMedicineModels.get(i).setSelected(false);

        }
        notifyDataSetChanged();

    }

    public List<Medicine> getMedicines() {
        List<Medicine> res = new ArrayList<>(mWords.size());
        res.clear();
        for (int i = 0; i < mWords.size(); i++) {
            if (simpleMedicineModels.get(i).isSelected()) {
                res.add(mWords.get(i));
            }
        }
        return res;
    }


    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private View view;

        WordViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.simplify_drug_name);
        }

    }
}
