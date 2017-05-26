package com.example.pranijareddy.knowyourgovernment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Pranijareddy on 4/5/2017.
 */

public class OfficialAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Official> offList;
    private MainActivity mainAct;

    public OfficialAdapter(List<Official> sList, MainActivity ma) {
        this.offList = sList;
        mainAct = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview, parent, false);

      itemView.setOnClickListener(mainAct);
       // itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Official off = offList.get(position);
            holder.Office.setText(off.getOffice());
            holder.official.setText(off.getName()+"( "+off.getParty()+" )");
            holder.imageView.setImageResource(R.drawable.separator);

    }

    @Override
    public int getItemCount() {
        return offList.size();
    }
}
