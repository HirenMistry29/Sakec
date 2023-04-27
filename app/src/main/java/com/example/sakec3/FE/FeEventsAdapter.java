package com.example.sakec3.FE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakec3.Events.Eventsgetset;
import com.example.sakec3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class FeEventsAdapter extends FirebaseRecyclerAdapter<Eventsgetset, FeEventsAdapter.myViewHolder> {


    public FeEventsAdapter(@NonNull FirebaseRecyclerOptions<Eventsgetset> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Eventsgetset model) {
        //Read Text

        model.getSelectyear();

            holder.Title.setText(model.getTitle());
            holder.Description.setText((model.getDescription()));
            holder.RegLink.setText(model.getReglink());


            //Read Image
            Picasso.get()
                    .load(model.getImage())
                    .into(holder.img);


        }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventsfeed,parent,false);

        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView Title , Description , RegLink ;


        public myViewHolder(@NonNull View itemView){
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.eventbanner);
            Title = (TextView)itemView.findViewById(R.id.eventtitle);
            Description = (TextView)itemView.findViewById(R.id.description);
            RegLink=(TextView)itemView.findViewById(R.id.RegLink);

        }

    }

}
