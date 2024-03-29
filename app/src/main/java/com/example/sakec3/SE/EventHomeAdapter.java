package com.example.sakec3.SE;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakec3.Events.EventsAdapter;
import com.example.sakec3.Events.Eventsgetset;
import com.example.sakec3.Events.GradientUtils;
import com.example.sakec3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class EventHomeAdapter extends FirebaseRecyclerAdapter<Eventsgetset,EventHomeAdapter.myViewHolder> {

    public EventHomeAdapter(@NonNull FirebaseRecyclerOptions<Eventsgetset> options) {
        super(options);
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_homefeed,parent,false);

        return new EventHomeAdapter.myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Eventsgetset model) {
        model.getSelectyear();

        holder.Title.setText(model.getTitle());

        //Read Image
//        Picasso.get()
//                .load(model.getImage())
//                .into(holder.img);

        // create an array of random color codes
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA};

        // set the background color of each RecyclerView item to a random color
        Random rand = new Random();
        int randomColor = colors[rand.nextInt(colors.length)];
        holder.itemView.setBackgroundColor(randomColor);


    }


    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView Title, Description, RegLink;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.Eventtitlehome);
            img = (ImageView)itemView.findViewById(R.id.eventimage);



        }
    }
}
