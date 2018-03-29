package com.qmuiteam.qmuidemo.fragment.components.rxjava;

import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmuidemo.R;
import com.qmuiteam.qmuidemo.base.BaseFragment;
import com.qmuiteam.qmuidemo.lib.Group;
import com.qmuiteam.qmuidemo.lib.annotation.Widget;
import com.qmuiteam.qmuidemo.manager.QDDataManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Oasis Yao
 *         Created on 2018/3/20.
 */

@Widget(group = Group.Other, name = "countdown")
public class QDCountDownFragment extends BaseFragment{

    private static final int COUNT_DOWN_START_TIME_SECOND = 0;
    private static final int COUNT_DOWN_END_TIME_SECOND = 10;
    private static final int COUNT_DOWN_PERIOD_SECOND = 1;

    @BindView(R.id.topbar) QMUITopBar mTopBar;
    @BindView(R.id.count_down_button) QMUIRoundButton mButton;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_count_down,null);
        ButterKnife.bind(this, view);
        mQDItemDescription = QDDataManager.getInstance().getDescription(this.getClass());
        initTopBar();
        return view;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mTopBar.setTitle(mQDItemDescription.getName());
    }

    @OnClick(R.id.count_down_button)
    void onClick(View view){
        switch (view.getId()){
            case R.id.count_down_button:
                startCountDown();
                break;
        }
    }

    private void startCountDown(){
        mButton.setEnabled(false);
        addDisposable(
                Flowable.intervalRange(COUNT_DOWN_START_TIME_SECOND,
                        COUNT_DOWN_END_TIME_SECOND + 1,
                        0,
                        COUNT_DOWN_PERIOD_SECOND,
                        TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mButton.setText(COUNT_DOWN_END_TIME_SECOND - aLong +"");
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mButton.setEnabled(true);
                        mButton.setText("开始倒计时");
                    }
                }).doOnCancel(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }).subscribe()
        );
    }

}
