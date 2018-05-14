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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Oasis Yao
 *         Created on 2018/3/22.
 *
 *
 */
@Widget(group = Group.Other,name = "flatmap")
public class QDFlatMapFragment extends BaseFragment{

    private OkHttpClient okHttpClient;

    @BindView(R.id.topbar)QMUITopBar mTopBar;
    @BindView(R.id.start)QMUIRoundButton mButton;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_flatmap,null);
        ButterKnife.bind(this, view);
        mQDItemDescription = QDDataManager.getInstance().getDescription(this.getClass());
        initTopBar();
        initHttpClient();
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

    private void initHttpClient() {
        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * app get wechat username.
     */
    private void start(){
        addDisposable(Flowable.create(new FlowableOnSubscribe<VerifyInfo>() {
            @Override
            public void subscribe(final FlowableEmitter<VerifyInfo> floe) throws Exception {
                String url =  "https://api.weixin.qq.com/sns/oauth2/access_token?"
                        +"appid="+""
                        +"&secret=" +""
                        +"&code=" + ""
                        +"&grant_type=authorization_code";
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        floe.onNext(new VerifyInfo());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        VerifyInfo verifyInfo = new VerifyInfo();
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            verifyInfo.openid = object.getString("openid");
                            verifyInfo.token = object.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        verifyInfo.success = true;
                        floe.onNext(verifyInfo);
                    }
                });

            }
        }, BackpressureStrategy.ERROR)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<VerifyInfo, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(final VerifyInfo verifyInfo) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<String>() {
                            @Override
                            public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
                                final String name = "";

                                if(verifyInfo != null && verifyInfo.success) {
                                    String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                                            verifyInfo.token+
                                            "&openid=" +
                                            verifyInfo.openid;
                                    Request request = new Request.Builder()
                                            .url(url)
                                            .get()
                                            .build();
                                    okHttpClient.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            emitter.onNext("");
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {

                                            try {
                                                JSONObject object = new JSONObject(response.body().string());
                                                verifyInfo.name = object.getString("nickname");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            emitter.onNext(name);
                                        }
                                    });
                                }else {
                                    emitter.onNext("");
                                }
                            }
                        },BackpressureStrategy.ERROR);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        }));
    }

    private static class VerifyInfo{
        String token;
        String openid;
        String name;
        boolean success;
    }
}
