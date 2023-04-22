package com.example.sakec3.Events;

import static java.security.AccessController.getContext;

import android.content.ContentResolver;
import android.content.Context;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.example.sakec3.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventsAdapter extends FirebaseRecyclerAdapter<Eventsgetset,EventsAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventsAdapter(@NonNull FirebaseRecyclerOptions<Eventsgetset> options) {
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
