package com.example.second.getlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button button;
    TextView user;
    EditText UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button= (Button) findViewById(R.id.button);
        user= (TextView) findViewById(R.id.user);
        UserId= (EditText) findViewById(R.id.user_id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(LoginActivity.this,LDEActivity.class);
                i.putExtra("UserId",UserId.getText().toString());
                startActivity(i);


            }

        });
    }
}


