package com.helladank.lumohacks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

/**
 * Created by taekw on 2017-09-16.
 */

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarView mCalendarView;
    private ImageButton redX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = (CalendarView) findViewById(R.id.calendar_view);
        redX = (ImageButton) findViewById(R.id.redX);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "/" + i1 + "/" + i2;
                Log.d(TAG, "onSelectedDayChange: date: " + date);
            }
        });

        redX.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                System.out.println("Oh Yeah!");
            }
        });
    }
}
