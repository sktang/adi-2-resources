package com.example.kitty.myapplication.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitty.myapplication.helpers.DatabaseHelper;
import com.example.kitty.myapplication.adapters.HistoryRecViewAdapter;
import com.example.kitty.myapplication.R;
import com.example.kitty.myapplication.models.Walk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kitty on 8/25/16.
 */
public class HistoryListFragment extends Fragment {

    private static final String TAG = "HistoryFrag";

    private List<Walk> history = new ArrayList<>();
    private DatabaseHelper helper;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = DatabaseHelper.getInstance(getActivity());
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_history_recycler_view);
        rvLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rvLayoutManager);

        rvAdapter = new HistoryRecViewAdapter(history);
        recyclerView.setAdapter(rvAdapter);

        return rootView;
    }

    //get data depending on if query or default from VICE API
    private void getData() {

        final Cursor cursor = helper.getHistoryList();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Walk temp = new Walk();
            temp.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DataEntryHistory.COL_DATE)));
            temp.setTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DataEntryHistory.COL_TIME)));
            temp.setDistance(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.DataEntryHistory.COL_DISTANCE)));
            temp.setTotalTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DataEntryHistory.COL_TOTAL_TIME)));
            history.add(temp);
        }
    }

}
