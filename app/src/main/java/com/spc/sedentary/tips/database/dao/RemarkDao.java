package com.spc.sedentary.tips.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.spc.sedentary.tips.database.helper.DBHelper;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.utils.Constant;

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

    public boolean insert(RemarkEntity entity) {
        try {
            frameDao.create(entity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(RemarkEntity entity) {
        try {
            frameDao.delete(entity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllComplete() {
        try {
            DeleteBuilder<RemarkEntity, Integer> workFrameEntityIntegerDeleteBuilder = frameDao.deleteBuilder();
            workFrameEntityIntegerDeleteBuilder.where().eq("status", Constant.REMARK_STATUS_COMPLETE);
            workFrameEntityIntegerDeleteBuilder.delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean update(RemarkEntity entity) {
        try {
            frameDao.update(entity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public List<RemarkEntity> queryNormalAll() {
        List<RemarkEntity> list = new ArrayList<>();
        try {
            list = frameDao.queryBuilder()
                    .orderBy("position", true)
                    .where().eq("status", Constant.REMARK_STATUS_NORMAL)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RemarkEntity> queryCompletelAll() {
        List<RemarkEntity> list = new ArrayList<>();
        try {
            list = frameDao.queryBuilder().where().eq("status", Constant.REMARK_STATUS_COMPLETE).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
