package com.usepjh92.user.myfamilyband.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.MoreActivity;
import com.usepjh92.user.myfamilyband.item.Item;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Frag2RecyclerAdapter extends RecyclerView.Adapter<Frag2RecyclerAdapter.ViewHolder>{

    ArrayList<Item> items;
    Context context;
    View itemView;

    public Frag2RecyclerAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public Frag2RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(context).inflate(R.layout.frag2_iist_item , parent , false);

        ViewHolder holder = new ViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(Frag2RecyclerAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(items.get(position).name);
        holder.tvDate.setText(items.get(position).date);
        holder.tvDesc.setText(items.get(position).desc);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView tvName;
        TextView tvDate;
        TextView tvDesc;
        ImageView imgDesc;
        VideoView videoDesc;

        public ViewHolder(final View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img);
            tvName = (TextView)itemView.findViewById(R.id.tv_title);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);
            tvDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            imgDesc = (ImageView)itemView.findViewById(R.id.img_view);
            videoDesc = (VideoView)itemView.findViewById(R.id.video_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , MoreActivity.class);
                    context.startActivity(intent);
                }
            });


        }
    }

}
