package com.actor.testapplication.utils;

import com.blankj.utilcode.util.TimeUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;

/**
 * description: Greendao, String转Date,
 *
 * @author : ldf
 * date       : 2021/6/25 on 21
 * @version 1.0
 */
public class String2DateConverter implements PropertyConverter<Date, String> {

    @Override
    public Date convertToEntityProperty(String databaseValue) {
        return TimeUtils.string2Date(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Date entityProperty) {
        return TimeUtils.date2String(entityProperty);
    }
}
