package com.qmuiteam.qmui.util;

import android.content.Context;
import android.support.annotation.MainThread;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author Oasis Yao
 *         Created on 2018/4/2.
 */

public class QMUIToastHelper {

    private static WeakReference<Toast> sToast;

    @MainThread
    public static void showToast(Context context,CharSequence text,int duration){
        if(sToast != null && sToast.get()!= null){
            sToast.get().cancel();
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        sToast = new WeakReference<Toast>(toast);
    }
}
