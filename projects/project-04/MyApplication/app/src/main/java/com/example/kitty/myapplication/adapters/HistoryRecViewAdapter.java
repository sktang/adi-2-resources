package com.example.kitty.myapplication.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kitty.myapplication.R;
import com.example.kitty.myapplication.models.Walk;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kitty on 8/25/16.
 */
public class HistoryRecViewAdapter extends RecyclerView.Adapter<HistoryRecViewAdapter.SampleViewHolder> {

    private List<Walk> data;

    public HistoryRecViewAdapter(List<Walk> inComingData) {
        this.data = inComingData;
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView distance;
        private TextView totalTime;

        public SampleViewHolder(final View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.history_list_item_date);
            this.distance = (TextView) itemView.findViewById(R.id.history_list_item_distance);
            this.totalTime = (TextView) itemView.findViewById(R.id.history_list_item_total_time);
        }
    }


    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get context from parent ViewGroup
        Context context = parent.getContext();

        // Get layoutInflater, which will inflate our custom list item layout for us
        LayoutInflater inflater = LayoutInflater.from(context);

        /**
         * Inflate the custom list item layout. The view returned back is our top level
         * view. If you look at step 0, you'll see our top level layout is LinearLayout.
         *
         * We pass this LinearLayout view to our SampleViewHolder so we can pull our
         * TextView out of it via their id's
         */
        View listItemLayout = inflater.inflate(R.layout.history_list_item, parent, false);

        // Return a new SampleViewHolder instance
        SampleViewHolder viewHolder = new SampleViewHolder(listItemLayout);
        return viewHolder;
    }

    @TargetApi(24)
    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        // Get our data item for the current position from the data list
        Walk dataItem = data.get(position);

        /**
         * Pull out the inflated TextView references out of our SampleViewHolder
         * instance.
         *
         * Look at the constructor of SampleViewHolder() and note that variable fields
         * 'imageView' and 'textView' are both public ( which is why we don't need a getter ).
         */
        TextView dateTextView = holder.date;
        TextView distanceTextView = holder.distance;
        TextView totalTimeTextView = holder.totalTime;

        // put our dataItem string as text into the text view
        dateTextView.setText(dataItem.getDate()+ " " + dataItem.getTime());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        distanceTextView.setText(df.format(dataItem.getDistance())+ " m");
        totalTimeTextView.setText(dataItem.getTotalTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
