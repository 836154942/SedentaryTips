package com.spc.sedentary.tips.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.spc.sedentary.tips.database.helper.DBHelper;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spc on 2017/3/2.
 */

public class RemarkDao {

    private Dao<RemarkEntity, Integer> frameDao;
    private DBHelper helper;

    public RemarkDao(Context context) {
        try {
            helper = DBHelper.getHelper(context);
            frameDao = helper.getDao(RemarkEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(RemarkEntity entity) {
        try {
            frameDao.create(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(RemarkEntity entity) {
        try {
            DeleteBuilder<RemarkEntity, Integer> workFrameEntityIntegerDeleteBuilder = frameDao.deleteBuilder();
            workFrameEntityIntegerDeleteBuilder.where().eq("id", entity.getId());
            workFrameEntityIntegerDeleteBuilder.delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void update(RemarkEntity entity) {
        try {
            frameDao.update(entity);
        } catch (SQLException e) {

        }
    }


    public RemarkEntity queryForFrameId(String id) {

        try {
            List<RemarkEntity> list = frameDao.queryForEq("id", id);
            return list == null ? null : list.size() == 0 ? null : list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<RemarkEntity> queryForAll() {
        List<RemarkEntity> list = new ArrayList<>();
        try {
            list = frameDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
