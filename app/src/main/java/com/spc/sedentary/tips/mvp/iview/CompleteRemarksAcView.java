package com.spc.sedentary.tips.mvp.iview;

import com.spc.sedentary.tips.base.BaseMvpViewInterface;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;

import java.util.List;

/**
 * Created by spc on 2017/9/13.
 */

public interface  CompleteRemarksAcView extends BaseMvpViewInterface{
    void getAllCompleteData(List<RemarkEntity> remarkEntities);
    void deleteAllComplete(Boolean res);
}
