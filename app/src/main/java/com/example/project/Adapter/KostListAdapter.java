package com.example.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DetailActivity;
import com.example.project.ListActivity;
import com.example.project.Model.Boarding;
import com.example.project.R;
import com.example.project.Storage.BoardingStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KostListAdapter extends RecyclerView.Adapter<KostListAdapter.MyViewHolder> {

    private ArrayList<Boarding> boardings;
    Context ctx;
    public KostListAdapter(Context context, ArrayList<Boarding> boardings){
        this.ctx = context;
        this.boardings = boardings;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.adapter_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.name.setText("Name : "+boardings.get(position).getName());
        holder.price.setText("Price : "+boardings.get(position).getPrice());
        holder.facility.setText("Facilty : "+boardings.get(position).getFacility());
        Picasso.get().load(boardings.get(position).getImages()).into(holder.imageKos);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),boardings.get(holder.getAdapterPosition()).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("id",boardings.get(holder.getAdapterPosition()).getId());
                extras.putString("nama",boardings.get(holder.getAdapterPosition()).getName());
                extras.putString("fasilitas",boardings.get(holder.getAdapterPosition()).getFacility());
                extras.putString("harga",""+boardings.get(holder.getAdapterPosition()).getPrice());
                extras.putString("deskripsi",boardings.get(holder.getAdapterPosition()).getDescription());
                extras.putString("latitude",boardings.get(holder.getAdapterPosition()).getLatitude());
                extras.putString("longitude",boardings.get(holder.getAdapterPosition()).getLongitude());
                extras.putString("gambar",boardings.get(holder.getAdapterPosition()).getImages());
                intent.putExtras(extras);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boardings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,facility;
        ImageView imageKos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameKos);
            price = itemView.findViewById(R.id.priceKos);
            facility = itemView.findViewById(R.id.facilityKos);
            imageKos = itemView.findViewById(R.id.imageView);
        }
    }
}
