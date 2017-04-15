package com.regongzaixian.jiankong.main.presenter;

import com.regongzaixian.jiankong.main.frame.IMainPresenter;
import com.regongzaixian.jiankong.main.frame.IMainView;
import com.regongzaixian.jiankong.model.InstrumentEntity;
import com.regongzaixian.jiankong.net.NetManager;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

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
        Observable<List<InstrumentEntity>> observable = NetManager.getInstance().getApiService().queryInstruments();
        NetManager.getInstance().runRxJava(observable, new Subscriber<List<InstrumentEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getiView().toast(e.getMessage());
                getiView().queryFail();
            }

            @Override
            public void onNext(List<InstrumentEntity> instrumentEntities) {
                getiView().querySuccess(instrumentEntities);
            }
        });
    }
}
