package com.helladank.lumohacks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by terb on 16/09/17.
 */

public class CommunityTab extends Fragment {
    // JSON Node names
    private static final String TAG = "CommunityTab";
    private static final String TAG_FIRST_NAME = "firstname";
    private static final String TAG_LAST_NAME = "lastname";
    private static final String TAG_USER_NAME = "username";
    private static final String TAG_GOAL = "goal";
    private static final String TAG_STREAK = "streak";

    // url to connect to
    private static final String url = "https://stormy-everglades-33980.herokuapp.com/users/loadfriends";

    // listview
    private ListView listView;
    private View rootView;
    private String jsonStr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.community_tab, container, false);
        listView = rootView.findViewById(R.id.community_list);

        ArrayList<HashMap<String, String>> usersList;
        usersList = parseJSON(jsonStr);
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), usersList,
                R.layout.community_user, new String[]{TAG_FIRST_NAME, TAG_LAST_NAME,
                TAG_USER_NAME, TAG_GOAL, TAG_STREAK}, new int[]{R.id.firstName,
                R.id.lastName, R.id.userName, R.id.goal, R.id.streak});

        listView.setAdapter(adapter);

        return rootView;
    }

    private ArrayList<HashMap<String, String>> parseJSON(String json) {
        if (json != null) {
            try {
// Hashmap for ListView
                ArrayList<HashMap<String, String>> usersList = new ArrayList<HashMap<String, String>>();
                JSONObject jsonObj = new JSONObject(json);

// Getting JSON Array node
                JSONArray users = jsonObj.getJSONArray("body");

// looping through All Students
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);

                    String firstName = c.getString(TAG_FIRST_NAME);
                    String lastName = c.getString(TAG_LAST_NAME);
                    String username = c.getString(TAG_USER_NAME);
                    String goal = c.getString(TAG_GOAL);
                    String streak = c.getString(TAG_STREAK);

// tmp hashmap for single user
                    HashMap<String, String> user = new HashMap<String, String>();

// adding every child node to HashMap key => value
                    user.put("firstname", firstName);
                    user.put("lastname", lastName);
                    user.put("username", username);
                    user.put("goal", goal);
                    user.put("streak", streak);
// adding student to students list
                    usersList.add(user);
                }
                return usersList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


}
