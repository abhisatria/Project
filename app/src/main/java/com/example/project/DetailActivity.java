package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Model.Boarding;
import com.example.project.Model.Booking;
import com.example.project.Storage.BoardingStorage;
import com.example.project.Storage.BookingStorage;
import com.example.project.Storage.UserLoginStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ImageView imgKos;
    TextView tvName,tvFacility,tvPrice,tvDescription,tvLatitude,tvLongitude,tvBook;
    Button buttonBook,buttonLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgKos = findViewById(R.id.gambarKos);
        tvName = findViewById(R.id.tvNameKos);
        tvFacility = findViewById(R.id.tvFacilityKos);
        tvPrice = findViewById(R.id.tvPriceKos);
        tvDescription = findViewById(R.id.tvDescription);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        buttonBook = findViewById(R.id.btBook);
        buttonLocation = findViewById(R.id.btMap);
        tvBook = findViewById(R.id.tvBook);
        getSupportActionBar().hide();
        final Double latitude,longitude;


        tvName.setText(getIntent().getStringExtra("nama"));
        tvFacility.setText(getIntent().getStringExtra("fasilitas"));
        tvPrice.setText(getIntent().getStringExtra("harga"));
        tvDescription.setText(getIntent().getStringExtra("deskripsi"));
        tvLatitude.setText(getIntent().getStringExtra("latitude"));
        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        tvLongitude.setText(getIntent().getStringExtra("longitude"));
        Picasso.get().load(getIntent().getStringExtra("gambar")).into(imgKos);

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapFormActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("latitude",latitude);
                extras.putDouble("longitude",longitude);
                extras.putString("title",getIntent().getStringExtra("nama"));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
                }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String userID = UserLoginStorage.userID;


        for(int i=0;i<BookingStorage.countItem;i++)
        {
            if(UserLoginStorage.userID.equals(BookingStorage.bookings.get(i).getUserID() ))
            {
               if(getIntent().getIntExtra("id",0)==(BoardingStorage.boardings.get(i).getId()))
               {
                   Intent intent = new Intent(this,ListActivity.class);
                   Toast.makeText(this, "Booking Failed. You already book this kos", Toast.LENGTH_SHORT).show();
                   break;
               }
            }
            if(i==BookingStorage.countItem-1)
            {
                String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//                BookingStorage.countItem++;
                UserDBAdapter userDBAdapter = new UserDBAdapter(getApplicationContext(),null,null,1);
                SQLiteDatabase obj = userDBAdapter.getReadableDatabase();
                userDBAdapter.onOpen();
                long id_=userDBAdapter.getBookingCount();
                userDBAdapter.close();
                String bookID = "BK"+id_/100+id_/10+id_%10;


                Booking booking =new Booking(bookID,userID,getIntent().getStringExtra("nama")
                        ,getIntent().getStringExtra("harga")
                        ,getIntent().getStringExtra("fasilitas")
                        ,currentDateString
                        ,getIntent().getStringExtra("deskripsi")
                        ,getIntent().getStringExtra("latitude")
                        ,getIntent().getStringExtra("longitude")
                        ,getIntent().getStringExtra("gambar"));

                userDBAdapter.onOpen();
                userDBAdapter.insertBookingTransaction(booking);
                userDBAdapter.close();
                BookingStorage.bookings.add(booking);
                Toast.makeText(this, "Booking Success", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(BookingStorage.countItem==0)
        {
            String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//            BookingStorage.countItem++;
            UserDBAdapter userDBAdapter = new UserDBAdapter(getApplicationContext(),null,null,1);
            SQLiteDatabase obj = userDBAdapter.getReadableDatabase();
            userDBAdapter.onOpen();
            long id_=userDBAdapter.getBookingCount();
            userDBAdapter.close();

            String bookID = "BK"+id_/100+id_/10+id_%10;

            Booking booking =new Booking(bookID,userID,getIntent().getStringExtra("nama"),getIntent().getStringExtra("harga"),getIntent().getStringExtra("fasilitas"),currentDateString,getIntent().getStringExtra("deskripsi"),getIntent().getStringExtra("latitude"),getIntent().getStringExtra("longitude"),getIntent().getStringExtra("gambar"));

            userDBAdapter.onOpen();
            userDBAdapter.insertBookingTransaction(booking);
            userDBAdapter.close();
            BookingStorage.bookings.add(booking);
//            Toast.makeText(this," "+userDBAdapter.allBookings(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Booking Success", Toast.LENGTH_SHORT).show();
        }

    }
}
