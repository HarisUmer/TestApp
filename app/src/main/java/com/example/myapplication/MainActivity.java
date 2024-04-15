package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(MainActivity.this));
        }

        setContentView(R.layout.activity_main);
        ImageView i1 = findViewById(R.id.img);
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.downward);
        i1.setAnimation(ani);
      //  Sender s1 = new Sender();
        Button button = findViewById(R.id.b1); // Assuming you have a button with id 'button' in activity_main.xml
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printHello();
            }
        });

    }

    public void SetText1()
    {
        EditText text = findViewById(R.id.Text1);// Switch content view to 'layout.xml'
        String  p = text.getText().toString();
        TextView t1 = findViewById(R.id.View1);
        t1.setText(p.toCharArray(),0,p.length());
    }
    public void printHello() {
        Intent i1 = new Intent(MainActivity.this,camera.class);
        startActivity(i1);
        //setContentView(R.layout.activity_camera);
    }
}