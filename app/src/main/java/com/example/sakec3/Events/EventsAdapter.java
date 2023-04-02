package com.example.sakec3.Events;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakec3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewAdapter> {
    private Context context;
    private ArrayList<Eventsgetset> list;

    public EventsAdapter(Context context, ArrayList<Eventsgetset> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eventsfeed,parent,false);

        return new EventsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewAdapter holder, int position) {
            Eventsgetset currentevent = list.get(position);
            holder.eventtitle.setText(currentevent.getTitle());
        try {
            if(currentevent.getImage()!=null)
            Picasso.get().load(currentevent.getImage()).into(holder.eventbanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventsViewAdapter extends RecyclerView.ViewHolder {
        public TextView eventtitle;
        public ImageView eventbanner;
        public EventsViewAdapter(@NonNull View itemView) {
            super(itemView);
            eventtitle=itemView.findViewById(R.id.eventtitle);
            eventbanner=itemView.findViewById(R.id.eventbanner);

        }
    }
}
