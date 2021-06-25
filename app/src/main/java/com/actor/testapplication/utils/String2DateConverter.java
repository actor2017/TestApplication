package com.actor.testapplication.utils;

import com.blankj.utilcode.util.TimeUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * description: Greendao, String转Date,
 *
 * @author : ldf
 * date       : 2021/6/25 on 21
 * @version 1.0
 */
public class String2DateConverter implements PropertyConverter<Date, String> {

    private static final List<String> FORMATS = Arrays.asList(
            "yyyy-MM-dd HH:mm:ss"
    );

    @Override
    public Date convertToEntityProperty(String databaseValue) {
        Date date = null;
        for (String format : FORMATS) {
//            try
                date = TimeUtils.string2Date(databaseValue, format);
                break;
        }
        return date;
    }

    @Override
    public String convertToDatabaseValue(Date entityProperty) {
        return TimeUtils.date2String(entityProperty);
    }
}
