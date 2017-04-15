package com.regongzaixian.jiankong.main.presenter;

import com.regongzaixian.jiankong.main.frame.IMainPresenter;
import com.regongzaixian.jiankong.main.frame.IMainView;

import android.os.Handler;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/4/4
 * Time: 下午3:37
 * Description:
 */
public class MainPresenterImpl extends IMainPresenter {

    @Override
    public void attachView(IMainView iView) {
        super.attachView(iView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void doQuery() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getiView().toast("刷新完成");
                getiView().querySuccess();
            }
        }, 3000);

    }
}
