package com.example.helloworld;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.graphics.Matrix;
import android.Manifest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<LoggedItem> logItems;

    private static final int REQUEST_CODE_PERMISSION = 123;

    private Target target;

    private static RecyclerViewClickListener listener;

    public List<LoggedItem> getItems() {
        return logItems;
    }

    public static RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }





    public LogAdapter(Context context, List<LoggedItem> logItems, RecyclerViewInterface recyclerViewInterface, RecyclerViewClickListener listener) {
        this.context = context;
        this.logItems = logItems;
        this.recyclerViewInterface = recyclerViewInterface;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogViewHolder(LayoutInflater.from(context).inflate(R.layout.log_item_view,parent, false), recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        holder.nameView.setText(logItems.get(position).getNameLogged());
        holder.dateTimeTextView.setText(logItems.get(position).getDate() + " " + logItems.get(position).getTime());
        holder.iconImageView.setImageResource(BirdDataImporter.fetchIcon(context.getApplicationContext(), logItems.get(position).getNameLogged()));
        if (!logItems.get(position).isCaptured()){
            holder.iconImageView.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            if (!logItems.get(position).isHeard()) {
                holder.iconImageView.setImageResource(R.drawable.pixelated_question_icon);
                holder.iconImageView.clearColorFilter();
            }
        }
        Uri seeLogImageUri = Uri.parse(logItems.get(position).getPhotoId());
        Picasso.get().setLoggingEnabled(true);
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("WE DOING BUT FAIL", logItems.get(position).getPhotoId());
                holder.userPhotoImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                holder.userPhotoImageView.setImageResource(R.drawable.error_paper);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.userPhotoImageView.setImageResource(R.drawable.loading_sand);
            }
        };

        Picasso.get().load(seeLogImageUri).into(target);


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
        return logItems.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void requestStoragePermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions((YourProfileActivity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        } else {
            // Permission is already granted, proceed with your operation

        }
    }
}

