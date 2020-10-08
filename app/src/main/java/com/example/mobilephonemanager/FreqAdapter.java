package com.example.mobilephonemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FreqAdapter extends RecyclerView.Adapter<FreqAdapter.ViewHolder> {
    @NonNull
    @Override
    public FreqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FreqAdapter.ViewHolder holder, int position) {
        AppItem app=mAppList.get(position);
        holder.appInfo.setText(app.getName()+"    "+app.getTimes()+"次");
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView appInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appInfo=(TextView)itemView.findViewById(R.id.app_info);
        }
    }
    private List<AppItem> mAppList;
    public FreqAdapter(List<AppItem> mAppList){
        this.mAppList=mAppList;
    }

}
