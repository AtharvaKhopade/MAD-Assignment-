package com.example.planeshooter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity
{
    EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=findViewById(R.id.editText);
    }
        public void startGame(View v)
        {
            if(e.getText().toString().trim().isEmpty()){
                e.setError("Please enter Username");
                e.requestFocus();
            }else {
            Log.i("ImageButton", "clicked");
            Intent intent = new Intent(this, StartGame.class);
            intent.putExtra("uName",e.getText().toString().trim());
            startActivity(intent);
            finish();
            }
        }
}