package com.spc.sedentary.tips.mvp.iview;

import com.spc.sedentary.tips.base.BaseMvpViewInterface;

import java.util.List;

/**
 * Created by spc on 2017/9/11.
 */

public interface RemarkListView extends BaseMvpViewInterface {
    void getDataSuccess(List<String> list);
}
