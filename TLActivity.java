package com.example.second.getlocation;


import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

public class TLActivity extends ActionBarActivity {

    com.firebase.client.Firebase ref;
    protected String UserId;
    protected Location CurrentLocation;
    protected String LastUpdateTime;
    private ProgressDialog mProgressDialog;
    private static String TAG = "TeamLeadActivity";
    ArrayList<User> list1;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1);


        // Actionbar back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        com.firebase.client.Firebase.setAndroidContext(this);
        mProgressDialog = new ProgressDialog(TLActivity.this);
        mProgressDialog.setTitle("BDE List");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

        list1=new ArrayList<User>();

        final CustomAdapter adapter=new CustomAdapter(this,list1);
        listview= (ListView) findViewById(R.id.ListTL);
        listview.setAdapter(adapter);


        ref = new com.firebase.client. Firebase("https://bdelocation.firebaseio.com/User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot Snapshot) {

                System.out.println("snapshot value:" + Snapshot.getValue());
                int i=0;
                for (DataSnapshot ds : Snapshot.getChildren()) {
                    System.out.println("Snapshot value: " + i + " :" + ds.getValue());
                    i++;
                    User obj = ds.getValue(User.class);

                    list1.add(obj);

                }
                adapter.notifyDataSetChanged();
                Log.i(TAG, String.valueOf(list1.size()));


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase error");

            }

        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;

            case R.id.menu_map:
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("getData", list1);
                startActivity(intent);
                return true;
        }
            return super.onOptionsItemSelected(item);

        }
    }














