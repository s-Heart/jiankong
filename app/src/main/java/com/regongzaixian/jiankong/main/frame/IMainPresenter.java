package com.regongzaixian.jiankong.main.frame;

import com.regongzaixian.jiankong.mvp.BasePresenter;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/3/28
 * Time: 下午9:56
 * Description:
 */
public abstract class IMainPresenter extends BasePresenter<IMainView> {

    public abstract void doQuery();
}
