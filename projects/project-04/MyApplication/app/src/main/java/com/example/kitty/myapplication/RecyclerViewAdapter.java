package com.example.kitty.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;

/**
 * Created by kitty on 8/23/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Business> data;
    private static OnRecyclerViewItemClickListener recyclerViewOnClickListener;

    public RecyclerViewAdapter(ArrayList<Business> data, OnRecyclerViewItemClickListener listener) {
        this.recyclerViewOnClickListener = listener;

        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<Business>();
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(Business currentBusiness);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView parkImage;
        public TextView parkName;

        public MyViewHolder(View itemView) {
            super(itemView);
            parkImage = (ImageView) itemView.findViewById(R.id.park_list_item_image);
            parkName = (TextView) itemView.findViewById(R.id.park_list_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    recyclerViewOnClickListener.onItemClick(data.get(getLayoutPosition()));
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemLayout = inflater.inflate(R.layout.park_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Business currentPark = data.get(position);

        TextView title = holder.parkName;
        ImageView image = holder.parkImage;


        title.setText(currentPark.name());
        Picasso picasso = new Picasso.Builder(holder.parkImage.getContext()).listener(new Picasso.Listener() {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        picasso.load(currentPark.imageUrl()).into(image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Log.d("Adapter", "Success");
            }

            @Override
            public void onError() {
                Log.d("Adapter", "No Success");

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
