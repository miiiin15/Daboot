package com.example.daboot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daboot.Login.MemberInfo;
import com.example.daboot.MatchingInfo;
import com.example.daboot.R;

import java.util.ArrayList;

public class MatchingAdapter extends RecyclerView.Adapter<MatchingAdapter.ViewHolder> {

    ArrayList<MemberInfo> items = new ArrayList<MemberInfo>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MemberInfo item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MemberInfo item) {
        items.add(item);
    }

    public void setItems(ArrayList<MemberInfo> items) {
        this.items = items;
    }

    public MemberInfo getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MemberInfo item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView_name);
            textView2 = itemView.findViewById(R.id.textView_sex);
            textView3 = itemView.findViewById(R.id.textView_area);
            textView4 = itemView.findViewById(R.id.textView_field);
        }

        public void setItem(MemberInfo item) {
            textView1.setText(item.getName());
            textView2.setText(item.getSex());
            textView3.setText(item.getArea());
            textView4.setText(item.getField());
        }

    }
}
