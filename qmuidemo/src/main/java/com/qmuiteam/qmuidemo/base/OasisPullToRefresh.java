package com.qmuiteam.qmuidemo.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * @author Oasis Yao
 * Created on 2018/9/6.
 */
public class OasisPullToRefresh extends PtrFrameLayout {

    public boolean isPullToRefresh = true;

    public OasisPullToRefresh(Context context) {
        super(context);
        init();
    }

    public OasisPullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OasisPullToRefresh(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        View oasisPullRefreshHeaderView = new OasisPullRefreshHeaderView(getContext());
        setHeaderView(oasisPullRefreshHeaderView);
        addPtrUIHandler((PtrUIHandler) oasisPullRefreshHeaderView);
        setResistance(2.0f);
        setRatioOfHeaderHeightToRefresh(1.0f);
        setDurationToClose(100);
        setDurationToCloseHeader(600);
        setPullToRefresh(false);
        setKeepHeaderWhenRefresh(true);
        disableWhenHorizontalMove(true);
    }


    public void autoPullRefresh(){
        autoRefresh();
    }

    public void pullRefreshComplete(){
        refreshComplete();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        final int action = e.getAction();

        if(isPullToRefresh || action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
            return super.dispatchTouchEvent(e);
        }

        return dispatchTouchEventSupper(e);

    }

    public void setPullRefreshEnable(boolean enable){
        this.isPullToRefresh = enable;
    }
}
