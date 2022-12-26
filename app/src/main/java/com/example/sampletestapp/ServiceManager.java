package com.example.sampletestapp;

import android.content.Context;
import android.widget.Toast;

// Created service Manager for nothing, timepass
public class ServiceManager {

    public boolean isNetworkAvaliable(Context context) {
        Toast.makeText( context, "SRVMAN isNerkavble", Toast.LENGTH_SHORT).show();
        return false;
    }
}