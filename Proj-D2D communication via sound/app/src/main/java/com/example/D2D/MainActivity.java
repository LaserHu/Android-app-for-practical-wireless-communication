package com.example.D2D;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnTask0, btnTask1, btnTask2, btnTask3, btnTask4, btnTask5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTask0 = findViewById(R.id.btnTask0);
        btnTask1 = findViewById(R.id.btnTask1);
        btnTask2 = findViewById(R.id.btnTask2);
        btnTask3 = findViewById(R.id.btnTask3);
        btnTask4 = findViewById(R.id.btnTask4);
        btnTask5 = findViewById(R.id.btnTask5);

        btnTask0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task0.class);
                startActivity(intent);
            }
        });

        btnTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task1.class);
                startActivity(intent);
            }
        });

        btnTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task2.class);
                startActivity(intent);
            }
        });

        btnTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task3.class);
                startActivity(intent);
            }
        });

        btnTask4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task4.class);
                startActivity(intent);
            }
        });

        btnTask5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Task5.class);
                startActivity(intent);
            }
        });


    }
}