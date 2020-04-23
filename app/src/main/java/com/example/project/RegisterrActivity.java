package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.project.Model.User;
import com.example.project.Storage.UserStorage;


import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterrActivity extends AppCompatActivity {
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-zA-Z])"+
                    ".{3,25}");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-Z])"+
                    ".{6,}");;
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(?=.*[0-9]+).{10,12}");
    EditText etUsername,etPassword,etConfirmPassword,etPhoneNumber;
    Button Register;
    RadioButton radioMen,radioWomen;
    CheckBox checkBoxAgreement;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerr);
        getSupportActionBar().hide();

        etUsername = findViewById(R.id.etRegisterUsername);
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);
        etPassword = findViewById(R.id.etRegisterPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        Register = findViewById(R.id.btnRegisterPeople);
        radioMen = findViewById(R.id.radioMen);
        datePicker = findViewById(R.id.datePickerBirth);
        checkBoxAgreement = findViewById(R.id.checkbox);
        radioWomen = findViewById(R.id.radioWomen);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    Intent intent = new Intent(RegisterrActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean validate()
    {
        String username,password,confirmPassword,number,gender,dateOfBirth;
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();
        number = etPhoneNumber.getText().toString();

        if(username.isEmpty())
        {
            Toast.makeText(RegisterrActivity.this,"Username must filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.length()<3 || username.length()>25)
        {
            Toast.makeText(RegisterrActivity.this,"Username must be between 3 - 25 characters",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!USERNAME_PATTERN.matcher(username).matches()){
            Toast.makeText(RegisterrActivity.this,"Username must contain at least 1 digit and alphabetic ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.isEmpty())
        {
            Toast.makeText(RegisterrActivity.this,"Password must filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 7)
        {
            Toast.makeText(RegisterrActivity.this,"Password must more than 6 characters",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!PASSWORD_PATTERN.matcher(password).matches())
        {
            Toast.makeText(RegisterrActivity.this,"Username must contain at least 1 digit,1 uppercase and 1 lowercase ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(confirmPassword))
        {
            Toast.makeText(RegisterrActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(number.isEmpty())
        {
            Toast.makeText(RegisterrActivity.this,"Number must filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(number.length()<10 || number.length()>12)
        {
            Toast.makeText(RegisterrActivity.this,"Number must be between 10-12 digits",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!radioWomen.isChecked() && !radioMen.isChecked())
        {
            Toast.makeText(RegisterrActivity.this,"Gender must be filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!checkBoxAgreement.isChecked())
        {
            Toast.makeText(RegisterrActivity.this,"Agreement must be checked",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(number.contains("[a-zA-Z]+"))
        {
            Toast.makeText(RegisterrActivity.this,"Phone Number must contain only numbers",Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i=0;i< UserStorage.users.size();i++)
        {
            if(username.equals(UserStorage.users.get(i).getUsername()))
            {
                Toast.makeText(RegisterrActivity.this,"Username already exists",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (radioMen.isChecked())
        {
            gender = radioMen.getText().toString();
        }
        else
        {
            gender = radioWomen.getText().toString();
        }
        dateOfBirth = ""+datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
        UserStorage.count++;
        String userID = "US"+UserStorage.count/100+UserStorage.count/10+UserStorage.count%10;
        User u =new User(username,password,number,gender,dateOfBirth,userID);
        UserStorage.users.add(u);
//        Toast.makeText(RegisterrActivity.this,username+"-"+password+"-"+number+"-"+gender+"-"+dateOfBirth+"-"+userID,Toast.LENGTH_LONG).show();
        return true;
    }
}
