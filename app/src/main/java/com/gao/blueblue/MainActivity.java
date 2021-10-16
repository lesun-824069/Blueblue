package com.gao.blueblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import cn.leancloud.LCException;
import cn.leancloud.LCObject;
import cn.leancloud.callback.LCCallback;
import cn.leancloud.session.LCConnectionManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //LCObject testObject = new LCObject("TestObject");
        //testObject.put("words", "Hello world!");
        //testObject.saveInBackground().blockingSubscribe();
    }
}