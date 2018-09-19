package com.infinity.tolet.appel.to_let.findview.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.tolet.appel.to_let.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<item> itemList;

    public Adapter(Context mContext, List<item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public Adapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.find_card_view, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Adapter.myViewHolder holder, int position) {

        holder.profile_pic.setImageResource(itemList.get(position).getPro_pic());
        holder.pname.setText(itemList.get(position).getName());
        holder.paddress.setText(itemList.get(position).getAddress());
        holder.type.setText(itemList.get(position).getType());
        holder.month.setText(itemList.get(position).getMonth());
        holder.price.setText(itemList.get(position).getPrice()+" Tk");

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
ImageView profile_pic;
TextView pname,paddress,price,type,month;

        public myViewHolder(View itemView) {
            super(itemView);

            profile_pic=itemView.findViewById(R.id.profile_pic);
            pname=itemView.findViewById(R.id.pro_name);
            paddress=itemView.findViewById(R.id.rent_address);
            price=itemView.findViewById(R.id.amount);
            type=itemView.findViewById(R.id.rent_type);
            month=itemView.findViewById(R.id.rent_month);
        }
    }
}



