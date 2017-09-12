package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by spc on 2017/9/11.
 */

public class RemarkListPresenter extends BasePresenter<RemarkListView> {
    RemarkService mService;

    @Inject
    public RemarkListPresenter() {
        mService = new RemarkService(TipsApplication.getInst());
    }

    public void getData() {
        List<RemarkEntity> list = mService.queryNormalAll();
        mvpView.getDataSuccess(list);
    }
}
