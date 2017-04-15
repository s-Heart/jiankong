package com.regongzaixian.jiankong.main.frame;

import com.regongzaixian.jiankong.model.InstrumentEntity;
import com.regongzaixian.jiankong.mvp.BaseView;

import java.util.List;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/4/4
 * Time: 下午3:37
 * Description:
 */
public interface IMainView extends BaseView {
    void querySuccess(List<InstrumentEntity> instrumentEntities);

    void queryFail();
}
