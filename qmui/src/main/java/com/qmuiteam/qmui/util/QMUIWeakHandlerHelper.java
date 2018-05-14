package com.qmuiteam.qmui.util;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @author Oasis Yao
 *         Created on 2018/4/2.
 * A Handler that keeps a {@link WeakReference} to an object.
 *
 * <p>Use this to prevent leaking an Activity or other Context while messages are still pending.
 * When you extend this class you <strong>MUST NOT</strong> use a non static inner class, or the
 * containing object will still be leaked.
 *
 * <p>See <a href="http://android-developers.blogspot.com/2009/01/avoiding-memory-leaks.html">
 * Avoiding memory leaks</a>.
 * <p>Source <a href="http://androidxref.com/8.0.0_r4/xref/packages/apps/TV/common/src/com/android/tv/common/WeakHandler.java">
 * WeakHandler</a>.
 */


public abstract class QMUIWeakHandlerHelper<T> extends Handler {

    private final WeakReference<T> mRef;

    /**
     * Constructs a new  handler with a weak reference to the given referent using the provided
     * Looper instead of the default one.
     *
     * @param looper The looper, must not be null.
     * @param ref the referent to track
     */
    public QMUIWeakHandlerHelper(@NonNull Looper looper, T ref) {
        super(looper);
        mRef = new WeakReference<>(ref);
    }

    /**
     * Constructs a new handler with a weak reference to the given referent.
     *
     * @param ref the referent to track
     */
    public QMUIWeakHandlerHelper(T ref) {
        mRef = new WeakReference<>(ref);
    }

    /**
     * Calls {@link #handleMessage(Message, Object)} if the WeakReference is not cleared.
     */
    @Override
    public void handleMessage(Message msg) {
        T referent = mRef.get();
        if(referent == null){
            return;
        }

        handleMessage(msg,referent);
    }

    /**
     * Subclasses must implement this to receive messages.
     *
     * <p>If the WeakReference is cleared this method will no longer be called.
     *
     * @param msg      the message to handle
     * @param referent the referent. Guaranteed to be non null.
     */
    protected abstract void handleMessage(Message msg, @NonNull T referent);
}
