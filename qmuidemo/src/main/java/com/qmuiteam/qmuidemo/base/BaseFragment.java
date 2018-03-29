package com.qmuiteam.qmuidemo.base;

import android.util.Log;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmuidemo.R;
import com.qmuiteam.qmuidemo.manager.QDPreferenceManager;
import com.qmuiteam.qmuidemo.model.QDItemDescription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseFragment extends QMUIFragment {

    private static final String TAG = "BaseFragment";
    protected QDItemDescription mQDItemDescription;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;

    public BaseFragment() {
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAndShowUpgradeTip();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"====BaseFragment====onDetach====");
        releaseDisposable();
    }

    private void checkAndShowUpgradeTip() {
        QDPreferenceManager preferenceManager = QDPreferenceManager.getInstance(getContext());
        if (preferenceManager.isNeedShowUpgradeTip()) {
            preferenceManager.setNeedShowUpgradeTip(false);
            String title = String.format(getString(R.string.app_upgrade_tip_title), QMUIPackageHelper.getAppVersion(getContext()));
            String message = "1. 分离出 arch 模块，用于 fragment 管理，支持手势返回\n" +
                    "2. 整理 QMUITopbar 的 theme，能够对 QMUITopbar 做更多的差异化处理\n" +
                    "3. 其它 bugfix: #125、#127、#132、#141";
            new QMUIDialog.MessageDialogBuilder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .addAction(R.string.ok, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog qmuiDialog, int i) {
                            qmuiDialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    protected void addDisposable(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }

    private void releaseDisposable(){
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }
}
