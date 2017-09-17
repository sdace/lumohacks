package com.helladank.lumohacks;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton btnAddGoal;
    private ArrayList<Button> goals = new ArrayList<>(0);
    private ArrayList<CalendarView> goalCalendars = new ArrayList<>(0);
    private ViewGroup insertPoint;
    private NestedScrollView scrollView;
    private int goalNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddGoal = (ImageButton) findViewById(R.id.add_goal);
        insertPoint = (ViewGroup) findViewById(R.id.insert_point);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        btnAddGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Button newGoal = new Button(getApplicationContext());
                newGoal.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        System.out.println("testy testy" + (goals.indexOf(newGoal) + 1));
                    }
                });
                goals.add(newGoal);
                insertPoint.addView(newGoal, goalNum);
                goalNum++;
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
