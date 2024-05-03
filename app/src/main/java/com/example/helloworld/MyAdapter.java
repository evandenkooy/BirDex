package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setFilteredList(List<Item> filteredList){
        this.items = filteredList;
        notifyDataSetChanged();
    }



    private static RecyclerViewClickListener listener;

    public static RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }





    public MyAdapter(Context context, List<Item> items, RecyclerViewInterface recyclerViewInterface, RecyclerViewClickListener listener) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.scientificNameView.setText(items.get(position).getScientificName());
        holder.birdNumberView.setText(items.get(position).getBirdNumber());
        holder.imageView.setImageResource(items.get(position).getBirdIcon());
        double resultRand = Math.round( Math.random() );
       /* if (resultRand == 0){
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        else{
            double resultRand2 = Math.round( Math.random() );
            if (resultRand2 == 0){
                holder.imageView.setImageResource(R.drawable.pixelated_question_icon);
                holder.imageView.clearColorFilter();
            }

        }

        */


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
