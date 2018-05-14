package com.qmuiteam.qmuidemo.fragment.components.rxjava;

import android.view.View;

import com.qmuiteam.qmuidemo.base.BaseFragment;

/**
 * @author Oasis Yao
 *         Created on 2018/4/3.
 */

public class QDThreadFragment extends BaseFragment {

    @Override
    protected View onCreateView() {

        return null;
    }

    private static class LocalThreadEntity{
        private long id;
        private String name;

        public LocalThreadEntity(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "LocalThreadEntity{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }



    private static class InnerThread extends Thread{

        private ThreadLocal<LocalThreadEntity> entityThreadLocal = new ThreadLocal<>();

        private LocalThreadEntity entity;

        public InnerThread(LocalThreadEntity entity,String name){
            super(name);
            this.entity = entity;
            entityThreadLocal.set(entity);
        }

        @Override
        public void run() {
            super.run();

        }

        public LocalThreadEntity getLocalEntity(){
            return entityThreadLocal.get();
        }
    }
}
