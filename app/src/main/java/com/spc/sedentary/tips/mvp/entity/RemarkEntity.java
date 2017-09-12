package com.spc.sedentary.tips.mvp.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by spc on 2017/9/12.
 */
@DatabaseTable(tableName = "tb_remark")
public class RemarkEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(width = 500)
    private String content;
    @DatabaseField
    private int color;
    @DatabaseField(width = 15)
    private String date;
    @DatabaseField(width = 1)
    private String status;
    @DatabaseField(width = 5)
    private String position;


    public int getId() {
        return id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
