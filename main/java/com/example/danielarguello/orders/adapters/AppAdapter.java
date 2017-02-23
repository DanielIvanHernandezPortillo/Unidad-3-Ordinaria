package com.example.danielarguello.orders.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danielarguello.orders.R;
import com.example.danielarguello.orders.model.App;

import java.util.List;

/**
 * Created by DANIEL ARGUELLO on 23/02/2017.
 */

public class AppAdapter  extends RecyclerView.Adapter<AppAdapter.AppViewHolder> implements View.OnClickListener{

    List<App> apps;
    View.OnClickListener listener;

    public AppAdapter (List<App> apps){
        this.apps = apps;
    }

    public View.OnClickListener getListener(){
        return  listener;
    }
    public void setListener (View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public AppAdapter.AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_layout,parent,false);
        AppViewHolder holder = new AppViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppAdapter.AppViewHolder holder, int position) {

        holder.tvTitle.setText(apps.get(position).getTitle());
        holder.tvDescription.setText(apps.get(position).getDescription());
        holder.ivApp.setImageResource(R.drawable.androidan);
        holder.setListener(this);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!= null){
            listener.onClick(v);
        }

    }
    public static class AppViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        CardView cvApp;
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivApp;
        ImageButton btEditApp;
        ImageButton btDeleteApp;
        View.OnClickListener listener;


        public AppViewHolder (View itemView){
            super(itemView);

            cvApp = (CardView) itemView.findViewById(R.id.cv_app);
            ivApp = (ImageView) itemView.findViewById(R.id.iv_app);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            btEditApp = (ImageButton) itemView.findViewById(R.id.bt_edit_app);
            btDeleteApp = (ImageButton) itemView.findViewById(R.id.bt_delete_app);
            btEditApp.setOnClickListener(this);
            btDeleteApp.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onClick(v);
            }
        }

        public void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }
    }




















}
