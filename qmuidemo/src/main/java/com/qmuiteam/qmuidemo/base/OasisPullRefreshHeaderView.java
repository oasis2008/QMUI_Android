package com.qmuiteam.qmuidemo.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.qmuiteam.qmuidemo.R;
import com.qmuiteam.qmuidemo.base.view.CustomBaseViewRelative;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author Oasis Yao
 * Created on 2018/9/6.
 */
public class OasisPullRefreshHeaderView extends CustomBaseViewRelative implements PtrUIHandler {

    private static final String TAG = "OasisPullRefreshHeaderView";

    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public OasisPullRefreshHeaderView(Context context) {
        super(context);

    }

    public OasisPullRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onCustomInflateViewFinish() {
        imageView = findViewById(R.id.header_image);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.oasis_refresh_header_view;
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Log.d(TAG,"onUIRefreshPrepare");
        imageView.setImageResource(R.drawable.pull_refresh);

        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        if(animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }

        imageView.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Log.d(TAG,"onUIRefreshBegin");

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        if(animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }

        imageView.setImageResource(R.drawable.beta_inke_loading_1);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
