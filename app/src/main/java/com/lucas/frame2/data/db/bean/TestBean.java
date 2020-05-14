package com.lucas.frame2.data.db.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class TestBean {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "user_name")
    public String username;

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
