package com.example.kitty.myapplication.services;

import android.util.Log;

import com.example.kitty.myapplication.R;
import com.example.kitty.myapplication.utilities.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by kitty on 8/26/16.
 */
public class Firebase extends FirebaseMessagingService {
    private static final int NOTIF_ID = 9999;

    private static final String TAG = "FireBaseGcm";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(TAG, "onMessageReceived: " + remoteMessage.getFrom());;
        Log.i(TAG, "onMessageReceived: title " + remoteMessage.getNotification().getTitle());
        Log.i(TAG, "onMessageReceived: body " + remoteMessage.getNotification().getBody());

        String title = "Message from the Cloud!";
        String content = remoteMessage.getNotification().getBody();

        //to update for the location of warning and show on map when clicked on notification
        Util.builNotification(title, content, R.mipmap.ic_launcher, NOTIF_ID, getApplicationContext());
    }
}