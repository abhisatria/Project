package com.example.project.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.BookTransaction;
import com.example.project.ListActivity;
import com.example.project.Model.Booking;
import com.example.project.R;
import com.example.project.Storage.BookingStorage;
import com.example.project.Storage.UserLoginStorage;
import com.example.project.UserDBAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookingTransactionAdapter extends RecyclerView.Adapter<BookingTransactionAdapter.MyViewHolder> {
    private ArrayList<Booking> bookings;
    Context context;

    public BookingTransactionAdapter(ArrayList<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingTransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_booking_transaction,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingTransactionAdapter.MyViewHolder holder, final int position) {
        Picasso.get().load(bookings.get(position).getGambarKos()).into(holder.imageKos);
//        holder.imageKos.setImageResource(bookings.get(position).getGambarKos());
        holder.tvNameKos.setText(bookings.get(position).getKosName());
        holder.tvBookingID.setText(bookings.get(position).getBookingID());
        holder.tvUserID.setText(bookings.get(position).getUserID());
        holder.tvFacilityKos.setText(bookings.get(position).getKosFacility());
        holder.tvPriceKos.setText(bookings.get(position).getKosPrice());
        holder.tvBookingDate.setText(bookings.get(position).getBookingDate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Booking cancelation");
                alert.setMessage("Do you want to cancel your booking ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDBAdapter dbAdapter = new UserDBAdapter(v.getContext(),null,null,1);
                        dbAdapter.onOpen();
                        boolean result = dbAdapter.deleteBooking(bookings.get(position).getBookingID());
                        notifyItemRemoved(position);
                        if(result)
                        {
                            Intent intent = new Intent(v.getContext(), ListActivity.class);
                            Toast.makeText(v.getContext(), "Your booking has been cancelled", Toast.LENGTH_SHORT).show();
                            BookingStorage.bookings.clear();

                            SQLiteDatabase obj = dbAdapter.getReadableDatabase();
                            if(obj!=null)
                            {
                                BookingStorage.bookings.addAll(dbAdapter.allBookings());
                            }
                            v.getContext().startActivity(intent);
                        }
                        dbAdapter.close();


                    }


                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
//                Toast.makeText(context, bookings.get(position).getKosName(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookingID,tvUserID,tvBookingDate,tvNameKos,tvPriceKos,tvFacilityKos;
        ImageView imageKos;

        public MyViewHolder(View view) {
            super(view);
            tvBookingID = view.findViewById(R.id.bookingId);
            tvUserID = view.findViewById(R.id.userID);
            tvBookingDate = view.findViewById(R.id.bookingDate);
            tvNameKos = view.findViewById(R.id.namaKos);
            tvPriceKos = view.findViewById(R.id.priceKos);
            tvFacilityKos = view.findViewById(R.id.facilityKos);
            imageKos = view.findViewById(R.id.imageKos);
        }

    }

}
