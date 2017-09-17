package com.helladank.lumohacks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton btnAddGoal;
    private ArrayList<Button> goals = new ArrayList<>(0);
    private ViewGroup insertPoint;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddGoal = (ImageButton) findViewById(R.id.add_goal);
        insertPoint = (ViewGroup) findViewById(R.id.insert_point);

        btnAddGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button newGoal = new Button(getApplicationContext());
                newGoal.setText("helo" + i);
                i++;
                goals.add(newGoal);
                insertPoint.addView(newGoal);
                btnAddGoal.setY(btnAddGoal.getY() + btnAddGoal.getHeight());
            }
        });
    }
}
