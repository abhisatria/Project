package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Model.User;
import com.example.project.Storage.BoardingStorage;
import com.example.project.Storage.UserLoginStorage;
import com.example.project.Storage.UserStorage;

import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText etUsername,etPassword;
    TextView test;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        UserDBAdapter userDBAdapter = new UserDBAdapter(this,null,null,1);
        SQLiteDatabase obj = userDBAdapter.getReadableDatabase();
        if(obj!=null)
        {
            UserStorage.users.addAll(userDBAdapter.allUsers());
        }
//        if(userDBAdapter.getUsersCount()>0)
//        {
//            UserStorage.users.addAll(userDBAdapter.allUsers());
//        }

//        Cursor cursor = userDBAdapter.getAllUsers();
//        cursor.moveToFirst();

//        Toast.makeText(getApplicationContext(),cursor.getString(0),Toast.LENGTH_SHORT).show();
        //test doang
//        Intent intent = new Intent(MainActivity.this,ListActivity.class);
//        startActivity(intent); //sampe sini test

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btLogin);
        btnRegister = findViewById(R.id.btRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterrActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = etUsername.getText().toString();
                String inputPassword = etPassword.getText().toString();
                if(inputUsername.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Username must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Password must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }


//                Cursor cursor = db.rawQuery("SELECT * FROM users",null);
//                if(cursor.moveToFirst()){
//                    while(!cursor.isAfterLast()){
//                        if(inputUsername.equals(cursor.getString(1)))
//                        {
//                            if(!inputPassword.equals(cursor.getString(2)))
//                            {
//                                Toast.makeText(MainActivity.this, "Username and Password doesn't match", Toast.LENGTH_SHORT).show();
//                                break;
//                            }
//                            else {
////                                BoardingStorage.boardings.clear();
//                                UserLoginStorage.userID = cursor.getString(0);
//                                Intent intent = new Intent(MainActivity.this,ListActivity.class);
//                                intent.putExtra("UserID",cursor.getString(0));
//                                startActivity(intent);
//                                break;
//                            }
//                        }
//                    }
//                    Toast.makeText(MainActivity.this, "Username doesn't exist", Toast.LENGTH_SHORT).show();
//                }
                ///
                for(int i=0;i<UserStorage.users.size();i++)
                {
                    if(inputUsername.equals(UserStorage.users.get(i).getUsername()))
                    {
                        if(!inputPassword.equals(UserStorage.users.get(i).getPassword()))
                        {
                            Toast.makeText(MainActivity.this, "Username and Password doesn't match", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else {
                            BoardingStorage.boardings.clear();
                            UserLoginStorage.userID = UserStorage.users.get(i).getUserID();
                            Intent intent = new Intent(MainActivity.this,ListActivity.class);
                            intent.putExtra("UserID",UserStorage.users.get(i).getUserID());
                            startActivity(intent);
                        }
                    }
                    else if (i==UserStorage.users.size()-1)
                    {
                        Toast.makeText(MainActivity.this, "Username doesn't exist", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(UserStorage.users.size()==0)
                {
                    Toast.makeText(MainActivity.this, "Username doesn't exist", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
