package com.helladank.lumohacks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by terb on 16/09/17.
 */

public class CommunityTab extends Fragment {
    String[] testArray = {"Test", "Test1", "Test2"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.community_tab, container, false);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.community_user, testArray);

        ListView listView = (ListView) rootView.findViewById(R.id.community_list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
