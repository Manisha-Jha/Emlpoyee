package com.jams.itsolution.employee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    private List<ListData> EmpList;

    public Context mContext;
    public AdapterInterface adapterInterface;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public RelativeLayout emp_item;
        public MyViewHolder(View view) {
            super(view);
            emp_item = (RelativeLayout)view.findViewById(R.id.emp_item);

            name = (TextView) view.findViewById(R.id.name);
//            gen = (TextView) view.findViewById(R.id.gen);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }


    public DataAdapter(List<ListData> EmpList, Context mContext) {
        this.EmpList = EmpList;
        this.mContext=mContext;
        adapterInterface = (AdapterInterface)mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listsdata_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListData empData = EmpList.get(position);
        holder.name.setText(empData.getName());
        holder.emp_item.setTag(position);
        holder.emp_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             int pos = (int) view.getTag();
                adapterInterface.itemClick(pos);
            }
        });

        Glide.with(mContext).load(empData.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return EmpList.size();
    }
}