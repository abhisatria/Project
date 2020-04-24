package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Storage.BoardingStorage;
import com.example.project.Storage.UserLoginStorage;
import com.example.project.Storage.UserStorage;

import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText etUsername,etPassword;
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //test doang
        Intent intent = new Intent(MainActivity.this,ListActivity.class);
        startActivity(intent); //sampe sini test

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
            }
        });
    }
}
