package com.example.helloworld;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView iconImageView, userPhotoImageView;
    TextView nameView, dateTimeTextView;




    public LogViewHolder(@NonNull View logItemView, ImageView iconImageView, ImageView userPhotoImageView, TextView nameView, TextView dateTimeTextView) {
        super(logItemView);
        this.iconImageView = iconImageView;
        this.nameView = nameView;
        this.userPhotoImageView = userPhotoImageView;
        this.dateTimeTextView = dateTimeTextView;
    }

    public LogViewHolder(@NonNull View logItemView, RecyclerViewInterface recyclerViewInterface) {
        super(logItemView);
        iconImageView = logItemView.findViewById(R.id.iconImageView);
        nameView = logItemView.findViewById(R.id.name);
        userPhotoImageView = logItemView.findViewById(R.id.userPhotoImageView);
        dateTimeTextView = logItemView.findViewById(R.id.dateTimeText);

        logItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyAdapter.getListener().onClick(itemView, getAdapterPosition());
    }
}
