package com.wd.tech.mypush.kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.JPushInterface
import org.json.JSONException
import org.json.JSONObject


class MyReceiver : BroadcastReceiver() {
    private var MyReceiver: String = "JPush"
    var type: String? = null
    var pushId: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        var bundle = intent!!.extras

        when {

            JPushInterface.ACTION_REGISTRATION_ID.equals(intent.action) -> {
                //用户注册成功
                var string = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                Log.i(MyReceiver, string)
            }
            JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.action) -> {
                //接受接下来的自定义消息
                var string = bundle.getString(JPushInterface.EXTRA_MESSAGE)
                Log.i(MyReceiver, string)
            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.action) -> {
                Log.i(MyReceiver, "[MyReceiver] 接收到推送下来的通知")
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                val content = bundle.getString(JPushInterface.EXTRA_MESSAGE)
                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
                //**************解析推送过来的json数据并存放到集合中 begin******************
                try {
                    val json = JSONObject(extra)
                    type = json.getString("type")
                    pushId = json.getString("pushId")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    return
                }

                Log.i(MyReceiver, "[MyReceiver] 接收到推送下来的通知" + "type====" + type + "pushId" + pushId)
                Log.d(MyReceiver, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")
            }
            JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.action) -> {
                Log.i(MyReceiver, "用户点击打开了通知")
            }


        }
    }
}