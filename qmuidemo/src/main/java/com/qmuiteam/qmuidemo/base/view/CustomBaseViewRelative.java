package com.qmuiteam.qmuidemo.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author Oasis Yao
 * Created on 2018/9/6.
 */
public abstract class CustomBaseViewRelative extends RelativeLayout {

    public Context context;
    public LayoutInflater layoutInflater;

    public CustomBaseViewRelative(Context context) {
        super(context);
        init(context);
    }

    public CustomBaseViewRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        try {

            if(getLayoutId() != -1){
                layoutInflater.inflate(getLayoutId(),this,true);
            }

            onCustomInflateViewFinish();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
    }

    protected abstract void onCustomInflateViewFinish();
    protected abstract int getLayoutId();

}
