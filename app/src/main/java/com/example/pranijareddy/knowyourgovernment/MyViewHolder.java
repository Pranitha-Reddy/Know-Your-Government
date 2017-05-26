package com.example.pranijareddy.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pranijareddy on 4/5/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView Office;
    public TextView official;
    public ImageView imageView;


    public MyViewHolder(View view) {
        super(view);
        Office=(TextView) view.findViewById(R.id.office);
        official=(TextView) view.findViewById(R.id.official);
        imageView= (ImageView) view.findViewById(R.id.sep);

    }
}
