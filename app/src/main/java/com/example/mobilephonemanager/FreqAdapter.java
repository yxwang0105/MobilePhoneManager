package com.example.mobilephonemanager;

import android.content.Context;
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
    public void onBindViewHolder(@NonNull final FreqAdapter.ViewHolder holder, final int position) {
        AppItem app=mAppList.get(position);
        holder.appInfo.setText(app.getName()+"    "+app.getTimes()+"次");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
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
    private Context mContext;
    private OnItemClickListener listener;
    public FreqAdapter(List<AppItem> mAppList,Context mContext,OnItemClickListener listener){
        this.mAppList=mAppList;
        this.mContext=mContext;
        this.listener=listener;
    }

}
