package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.BookingTransactionAdapter;
import com.example.project.Model.Booking;
import com.example.project.Storage.BookingStorage;

public class BookTransaction extends AppCompatActivity {
    TextView tvEmpty;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_transaction);

        recyclerView = findViewById(R.id.recyclerViewTransaction);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView.setHasFixedSize(true);

        BookingStorage.bookings.clear();

        UserDBAdapter userDBAdapter = new UserDBAdapter(this,null,null,1);
        SQLiteDatabase obj = userDBAdapter.getReadableDatabase();
        if(obj!=null)
        {
            BookingStorage.bookings.addAll(userDBAdapter.allBookings());
        }
        BookingStorage.countItem =  Integer.parseInt(String.valueOf(userDBAdapter.getBookingCount()));
        if(BookingStorage.countItem!=0)
        {
            tvEmpty.setVisibility(View.GONE);
        }

        BookingTransactionAdapter adapter = new BookingTransactionAdapter(BookingStorage.bookings, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void showAlertDialog (View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Booking cancelation");
        alert.setMessage("Do you want to cancel your booking ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BookTransaction.this, "Your booking has been cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
