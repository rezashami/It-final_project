package com.example.rezatanha.finalproject.Model.Medicine;

public class SimpleMedicineModel {
    private String text;
    private boolean isSelected = false;

    public SimpleMedicineModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
