package com.example.kitty.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kitty.myapplication.R;

/**
 * Created by kitty on 8/18/16.
 */
public class MainFragment extends Fragment {

    private Button startWalkButton, findParksButton, historyButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        setViews(v);
        setButtonOnClick();
        return v;
    }

    private void setViews(View v) {
        //finding views from xml
        startWalkButton = (Button) v.findViewById(R.id.fragment_main_start_walk_button);
        findParksButton = (Button) v.findViewById(R.id.fragment_main_find_parks_button);
        historyButton = (Button) v.findViewById(R.id.fragment_main_history_button);
    }

    private void setButtonOnClick () {
        startWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo: to go maps fragment
            }
        });

        findParksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo: go to maps fragment with parks mapped out
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo: go to history fragment (list frag)
            }
        });
    }
}
