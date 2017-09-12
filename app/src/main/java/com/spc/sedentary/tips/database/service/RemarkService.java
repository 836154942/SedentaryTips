package com.spc.sedentary.tips.database.service;

import android.content.Context;

import com.spc.sedentary.tips.database.dao.RemarkDao;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;

import java.util.List;

/**
 * Created by spc on 2017/9/12.
 */

public class RemarkService {
    RemarkDao mDao;

    public RemarkService(Context context) {
        mDao = new RemarkDao(context);
    }

    public void insert(RemarkEntity remarkEntity) {
        mDao.insert(remarkEntity);
    }

    public List<RemarkEntity> queryForAll() {
        return mDao.queryForAll();
    }
}
