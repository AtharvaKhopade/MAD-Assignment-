package com.example.planeshooter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOver extends Activity {
    TextView tvScore, tvPersonalBest,tvUsername;
    String Username;
    int score;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        SharedPreferences pref = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvPersonalBest = (TextView) findViewById(R.id.tvPersonalBest);
        tvUsername=(TextView) findViewById(R.id.uTv);
        //here get score and username from previous activity
        score=Integer.parseInt(String.valueOf(getIntent().getStringExtra("Score")));
        Username=getIntent().getStringExtra("un");
        tvScore.setText(score+"");
        tvUsername.setText("Username: "+Username);

        //now initialising firebase variables
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(Username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value=null;
                    value = String.valueOf(snapshot.getValue());
                    if(value.equals("null")){
                        firebaseDatabase=FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference("Users").child(Username);
                        tvPersonalBest.setText(score+"");
                        databaseReference.setValue(score);
                    }else {
                        int i = Integer.parseInt(String.valueOf(value.trim()));
                        if (i > score) {
                            tvPersonalBest.setText(i + "");
                        } else {
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("Users").child(Username);
                            tvPersonalBest.setText(score+"");
                            databaseReference.setValue(score+"");

                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "messahe - "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }

}
