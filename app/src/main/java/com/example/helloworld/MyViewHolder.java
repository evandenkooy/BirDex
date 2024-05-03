package com.example.helloworld;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView imageView;
    TextView nameView, scientificNameView, birdNumberView;




    public MyViewHolder(@NonNull View itemView, ImageView imageView, TextView nameView, TextView scientificNameView, TextView birdNumberView) {
        super(itemView);
        this.imageView = imageView;
        this.nameView = nameView;
        this.scientificNameView = scientificNameView;
        this.birdNumberView = birdNumberView;
    }

    public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        scientificNameView = itemView.findViewById(R.id.scientificname);
        birdNumberView = itemView.findViewById(R.id.birdNumber);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyAdapter.getListener().onClick(itemView, getAdapterPosition());
    }
}
