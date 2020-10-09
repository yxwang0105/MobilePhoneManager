package com.example.mobilephonemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComprehensiveFreqAdapter extends RecyclerView.Adapter<ComprehensiveFreqAdapter.ViewHolder> {
    @NonNull
    @Override
    public ComprehensiveFreqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comprehensive_app_item, parent, false);
        ComprehensiveFreqAdapter.ViewHolder holder = new ComprehensiveFreqAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppItem app=mAppList.get(position);
        holder.comprehensive_app_info.setText(app.getName()+"    "+app.getTime());
    }


    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView comprehensive_app_info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comprehensive_app_info = (TextView) itemView.findViewById(R.id.comprehensive_app_info);
        }
    }
    private List<AppItem> mAppList;
    private Context mContext;
    public ComprehensiveFreqAdapter(List<AppItem> mAppList,Context mContext){
        this.mAppList=mAppList;
        this.mContext=mContext;
    }
}
