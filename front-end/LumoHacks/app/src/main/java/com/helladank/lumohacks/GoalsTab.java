package com.helladank.lumohacks;

/**
 * Created by terb on 16/09/17.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class GoalsTab extends Fragment {

    public static final String GOAL_NUMBER = "com.helladank.GOAL_NUMBER";
    public static final String TAG = "GoalsTab";

    private ImageButton btnAddGoal;
    private ProgressBar progressBar;
    private ArrayList<Button> goals = new ArrayList<>(0);
    private ArrayList<String> goalNames = new ArrayList<>(0);

    // url to connect to
    private static final String url = "https://stormy-everglades-33980.herokuapp.com/loadgoals";
    private static final String saveUrl = "https://stormy-everglades-33980.herokuapp.com/savegoals";

    private ViewGroup insertPoint;
    private ScrollView scrollView;
    private View rootView;
    private int goalNum = 0;
    private ArrayList<String> goalsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        getGoals();



        rootView = inflater.inflate(R.layout.goals_tab, container, false);

        btnAddGoal = (ImageButton) rootView.findViewById(R.id.add_goal);
        insertPoint = (ViewGroup) rootView.findViewById(R.id.insert_point);
        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.indeterminateBar);

        btnAddGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Button newGoal = new Button(getActivity());
                newGoal.setBackgroundColor(0xFF8285C0);
                newGoal.setTextColor(0xFFFFFFFF);
                newGoal.setPaddingRelative(0, 8, 0, 0);

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
                                        goalNames.add(userInput.getText().toString());
                                        saveGoal(userInput.getText().toString());
                                        newGoal.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                                                intent.putExtra(GOAL_NUMBER, goals.indexOf(newGoal));
                                                startActivity(intent);
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

    private void getGoals() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String res = new String(response);

                Log.d(TAG, "success!");
                Log.d(TAG, res);
                goalsList = parseJSON(res);

                for (int i = 0; i < goalsList.size(); i++) {
                    final Button newGoal = new Button(getActivity());
                    newGoal.setBackgroundColor(0xFF8285C0);
                    newGoal.setTextColor(0xFFFFFFFF);
                    newGoal.setPaddingRelative(0, 8, 8, 0);

                    newGoal.setText(goalsList.get(i));
                    goals.add(newGoal);
                    insertPoint.addView(newGoal, i);

                    newGoal.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), CalendarActivity.class);
                            intent.putExtra(GOAL_NUMBER, goals.indexOf(newGoal));
                            startActivity(intent);
                        }
                    });
                }

                progressBar.setVisibility(View.INVISIBLE);
                btnAddGoal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private void saveGoal(String goalText) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("goal", goalText);

        client.post(saveUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String res = new String(response);

                Log.d(TAG, "success!");
                Log.d(TAG, res);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private ArrayList<String> parseJSON(String json) {
        if (json != null) {
            try {
// Hashmap for ListView
                ArrayList<String> goalsList= new ArrayList<String>();
                JSONObject jsonObj = new JSONObject(json);

// Getting JSON Array node
                JSONArray users = jsonObj.getJSONArray("body");

// looping through All Students
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);

                    String goal = c.getString("goal");
                    goalsList.add(goal);
                }
                return goalsList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
