package com.wd.tech.mypush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private static String type = null;
    private static String pushId = null;


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
           //比如判断应用是否在前台

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//            int msg_id = bundle.getInt(JPushInterface.EXTRA_MSG_ID);
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            //**************解析推送过来的json数据并存放到集合中 begin******************
            try {
                JSONObject json = new JSONObject(extra);
                type = json.getString("type");
                pushId = json.getString("pushId");
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }


            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知" + "type====" + type + "pushId" + pushId);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction()))

        {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction()))

        {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else

        {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }



}
