package com.example.kitty.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kitty.myapplication.R;
import com.yelp.clientlib.entities.Business;

/**
 * Created by kitty on 8/18/16.
 */
public class MainFragment extends Fragment {

    public static final String parkFragTag = "searchTag";

    private Button startWalkButton, findParksButton, historyButton;
    private ParksFragment parksFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        setViews(v);
        return v;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        setButtonOnClick();
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
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        findParksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (parksFragment == null) {
                    parksFragment = new ParksFragment();
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, parksFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
                // ToDo: go to history fragment (list frag)
            }
        });
    }
}
