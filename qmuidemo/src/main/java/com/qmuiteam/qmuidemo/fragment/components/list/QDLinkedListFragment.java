package com.qmuiteam.qmuidemo.fragment.components.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmuidemo.R;
import com.qmuiteam.qmuidemo.base.BaseFragment;
import com.qmuiteam.qmuidemo.lib.Group;
import com.qmuiteam.qmuidemo.lib.annotation.Widget;
import com.qmuiteam.qmuidemo.manager.QDDataManager;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Oasis Yao
 *         Created on 2018/3/13.
 */

@Widget(group = Group.Other, name = "LinkedList")
public class QDLinkedListFragment extends BaseFragment{

    @BindView(R.id.topbar) QMUITopBar mTopBar;

    private LinkedList<ListItem> mlist;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_linked_list, null);
        ButterKnife.bind(this, view);
        mQDItemDescription = QDDataManager.getInstance().getDescription(this.getClass());
        initTopBar();
        initList();
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

    private void initList() {
        mlist = new LinkedList<>();
    }

    private void addToList(ListItem item){
        mlist.add(item);

    }

    private static class ListItem{

    }

    private static class Container extends LinearLayout{

        public Container(Context context) {
            super(context);
        }

        public Container(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public Container(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


    }
}
