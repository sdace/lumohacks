package com.helladank.lumohacks;

/**
 * Created by terb on 16/09/17.
 */

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;

public class GoalsTab extends Fragment {

    private FloatingActionButton btnAddGoal;
    private ArrayList<Button> goals = new ArrayList<>(0);

    private ViewGroup insertPoint;
    private ScrollView scrollView;
    private int goalNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_tab, container, false);

        btnAddGoal = (FloatingActionButton) rootView.findViewById(R.id.add_goal);
        insertPoint = (ViewGroup) rootView.findViewById(R.id.insert_point);
        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);

        btnAddGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Button newGoal = new Button(getActivity());

                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        newGoal.setText(userInput.getText());
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
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });



        return rootView;
    }
}
