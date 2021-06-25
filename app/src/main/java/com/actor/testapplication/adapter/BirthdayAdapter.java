package com.actor.testapplication.adapter;

import androidx.annotation.NonNull;

import com.actor.testapplication.R;
import com.actor.testapplication.bean.BirthItem;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * description: 生日Adapter
 *
 * @author : ldf
 * date       : 2021/6/23 on 22
 * @version 1.0
 */
public class BirthdayAdapter extends BaseQuickAdapter<BirthItem, BaseViewHolder> {

    public BirthdayAdapter(List<BirthItem> items) {
        super(R.layout.item_birthday, items);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BirthItem item) {
        String birthday;
        Date birthdayDate = item.birthdayDate;
        if (birthdayDate != null) {
            birthday = TimeUtils.date2String(birthdayDate, "yyyy-MM-dd");
        } else {
            birthday = TimeUtils.date2String(birthdayDate = item.lunarCalendar, "MM-dd(阴历)");
        }

        String chineseZodiac = item.chineseZodiac == null ? TimeUtils.getChineseZodiac(birthdayDate) : item.chineseZodiac;
        helper.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_age, "年龄: " + getAge(birthdayDate))
                .setText(R.id.tv_chinese_zodiac, "生肖: " + chineseZodiac)
                .setText(R.id.tv_zodiac, TimeUtils.getZodiac(birthdayDate))//星座
                .setText(R.id.tv_birthday, birthday);
    }

    private int getAge(Date date) {
        //生日时间
        Calendar born = Calendar.getInstance();
        born.setTime(date);
        //现在时间
        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) - born.get(Calendar.MONTH);
        int day = current.get(Calendar.DAY_OF_MONTH) - born.get(Calendar.DAY_OF_MONTH);
        //如果现在还没到出生月份 or 就是本月,但是天数没到
        if (month < 0 || (month == 0 && day < 0)) {
            year --;
        }
        return year;
    }
}
