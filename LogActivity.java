package com.example.second.getlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class LogActivity extends AppCompatActivity {
Button TLbutton;
    Button BDEbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        TLbutton= (Button) findViewById(R.id.TL_button);
        BDEbutton= (Button) findViewById(R.id.BDE_button);

        TLbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LogActivity.this,TLActivity.class);
                startActivity(i);

            }
        });
        BDEbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LogActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });
}
}


