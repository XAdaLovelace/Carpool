package com.example.alphaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText getPhoneET;
    private Button getPhoneBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPhoneBTN = findViewById(R.id.getPhoneBTN);
        getPhoneET = findViewById(R.id.getPhoneET);

        getPhoneBTN.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String phone_num = getPhoneET.getText().toString().trim();

                if(phone_num.isEmpty()){
                    getPhoneET.setError("Valid Number is required");
                    getPhoneET.requestFocus();
                    return;
                }

                String complete_phone_num = "+972" + phone_num;
                Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", complete_phone_num);
                startActivity(intent);

            }
        }
        );
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null ){

            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        }

    }
}
