
package com.csc.catalina.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ciety.framework.logging.Logs;

public class CatalinaMessageSender {

    private static final String LOG_TAG = CatalinaMessageSender.class.getName();

    public static void sendMessage(int what, int arg1, int arg2, Object obj, Bundle data, Handler target) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        msg.setTarget(target);
        if (data != null) {
            msg.setData(data);
        }
        if (target != null) {
            msg.sendToTarget();
        } else {
            Logs.debug(LOG_TAG, "sendCietyMessage: did not send message as the target is null msg.what: " + what);
        }
    }

    public static void sendMessage(int what, int arg1, int arg2, Handler target) {
        sendMessage(what, arg1, arg2, null, null, target);
    }

    public static void sendMessage(int what, int arg1, Handler target) {
        sendMessage(what, arg1, -1, null, null, target);
    }

    public static void sendMessage(int what, Handler target) {
        sendMessage(what, -1, -1, null, null, target);
    }

    public static void sendMessage(int what, Object obj, Handler target) {
        sendMessage(what, -1, -1, obj, null, target);
    }

    public static void sendMessage(int what, int arg1, Object obj, Handler target) {
        sendMessage(what, arg1, -1, obj, null, target);
    }

    public static void sendMessage(int what, int arg1, Object obj, Bundle data, Handler target) {
        sendMessage(what, arg1, -1, obj, data, target);
    }

    public static void sendMessage(int what, Object obj, Bundle data, Handler target) {
        sendMessage(what, -1, -1, obj, data, target);
    }

    public static void sendMessage(int what, Bundle data, Handler target) {
        sendMessage(what, -1, -1, null, data, target);
    }

    public static void sendMessage(int what, int arg1, int arg2, Bundle data, Handler target) {
        sendMessage(what, arg1, arg2, null, data, target);
    }

    public static void sendMessage(int what, int arg1, Bundle data, Handler target) {
        sendMessage(what, arg1, -1, null, data, target);
    }

    /**
     * Sends a message to a specific handler.
     * 
     * @param handler the target handler.
     * @param identifier the integer identifier for the message.
     * @param payload the message payload. Can be null.
     */
    public static void sendMessageToHandler(Handler handler, int identifier, Bundle payload) {
        sendMessage(identifier, payload, handler);
    }
}
