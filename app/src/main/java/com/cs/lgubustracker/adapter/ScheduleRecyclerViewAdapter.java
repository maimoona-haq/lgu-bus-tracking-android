package com.cs.lgubustracker.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cs.lgubustracker.R;
import com.cs.lgubustracker.model.ScheduleModal;


import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {

    private List<ScheduleModal> mValues;
    //private OnListFragmentInteractionListener mListener;
    private Activity context;

    public ScheduleRecyclerViewAdapter(List<ScheduleModal> items, Activity context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.departure.setText(mValues.get(position).getSchedule());
        holder.arrival_time.setText(mValues.get(position).getArrivalTime());
        holder.departure_time.setText(mValues.get(position).getDepartureTime());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public  void updateList(List<ScheduleModal> mValues){
        this.mValues = mValues;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView departure,arrival_time,departure_time;
        public ScheduleModal mItem;

        public ViewHolder(View view) {


            super(view);
            mView = view;
            departure = (TextView) view.findViewById(R.id.departure);
            arrival_time = (TextView) view.findViewById(R.id.arrival_time);
            departure_time = (TextView) view.findViewById(R.id.departure_time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + departure.getText() + "'";
        }


    }
}
