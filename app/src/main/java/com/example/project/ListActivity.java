package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.project.Adapter.KostListAdapter;
import com.example.project.Model.Boarding;
import com.example.project.Storage.BoardingStorage;
import com.example.project.Storage.BookingStorage;
import com.example.project.Storage.UserLoginStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    private InputStream OpenHttpCoonnection(String urlString) throws IOException{
        InputStream inputStream = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        if(!(connection instanceof HttpURLConnection))
        {
            throw new IOException("Not an HTTP Connection");
        }
        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            response = httpURLConnection.getResponseCode();
            if(response==httpURLConnection.HTTP_OK)
            {
                inputStream = httpURLConnection.getInputStream();
            }
        }
        catch (Exception e){
            Log.d("Networking",e.getLocalizedMessage());
            throw new IOException("Error Connecting");
        }

        return inputStream;
    }

    public String readJSONFeed(String url){
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            inputStream = OpenHttpCoonnection(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log.d("Networking",e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public class AccessJSONServiceTask extends AsyncTask<String, Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return readJSONFeed(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONArray jsonArray = new JSONArray(s);
                Log.i("JSON","Number of surveys in feed: "+jsonArray.length());
                String gabungNama="";
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject= jsonArray.getJSONObject(i);

                    BoardingStorage.boardings.add(new Boarding(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("facilities"),jsonObject.getLong("price"),jsonObject.getString("address"),""+jsonObject.getDouble("LNG"),""+jsonObject.getDouble("LAT"),jsonObject.getString("image")));
                    gabungNama = gabungNama + BoardingStorage.boardings.get(i).getName();

                }
                KostListAdapter adapter = new KostListAdapter(ListActivity.this,BoardingStorage.boardings);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profil,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.Logout)
        {
            Toast.makeText(this, "Logout success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else if(id==R.id.BookingForm)
        {
            Intent i = new Intent(this,BookTransaction.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        BookingStorage.bookings.clear();

        UserDBAdapter userDBAdapter = new UserDBAdapter(this,null,null,1);
        SQLiteDatabase obj = userDBAdapter.getReadableDatabase();
        if(obj!=null)
        {
            BookingStorage.bookings.addAll(userDBAdapter.allBookings());
        }

        new AccessJSONServiceTask().execute("https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/MOBI6006/E202-MOBI6006-KR01-00.json");
//        Intent intent = getIntent();
//        String userID = intent.getStringExtra("UserID");
//        BoardingStorage.boardings.add(new Boarding(1,"Maharja","AC,WiFi,Bathroom",1450000,"The best boarding","-6.2000809","106.7833355",R.drawable.kost1));
//        BoardingStorage.boardings.add(new Boarding(2,"Haji Indra","AC,WiFi",1900000,"The cheapest boarding","-6.2261741","106.9078293",R.drawable.kost2));
//        Toast.makeText(ListActivity.this,BoardingStorage.boardings.get(1).getName(),Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, "UserID : "+ UserLoginStorage.userID, Toast.LENGTH_SHORT).show();

    }
}
