package com.actor.testapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.testapplication.R;
import com.actor.testapplication.activity.BirthdayDetailActivity;
import com.actor.testapplication.bean.BirthItem;
import com.actor.testapplication.utils.Global;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

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
        addChildClickViewIds(R.id.iv_head);
        setOnItemChildClickListener((adapter, view, position) -> showRemars(position));
        setOnItemClickListener((adapter, view, position) -> {
            if (!Global.IS_LIYONG_VERSION) {
                Activity activity = (Activity) getContext();
                BirthItem item = getItem(position);
                activity.startActivityForResult(new Intent(activity, BirthdayDetailActivity.class)
                        .putExtra(Global.id, item.id), 1
                );
            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BirthItem item) {
        //农历
        String lunarCalendar = item.lunarCalendar;
        if (lunarCalendar != null) {
            //1994-02-29 00:00:00, 这个农历对应的 阳历的2月 只有28天
            String ymd = lunarCalendar.split(" ")[0];
            lunarCalendar = ymd.concat("(农历)");
        }

        //当年阳历
        Date solarCalendar = item.solarCalendar;
        String birthday = null;
        if (solarCalendar != null) {
            birthday = TimeUtils.date2String(solarCalendar, "yyyy-MM-dd(新历)");
        }

        //下次生日
        Date nextSolarBirthday = item.getNextSolarBirthday();
        String nextSolarBirthdayStr = null;
        if (nextSolarBirthday != null) {
            nextSolarBirthdayStr = TimeUtils.date2String(nextSolarBirthday, "下次生日: yyyy-MM-dd");
        }

        long countDown = item.getCoundDownDay();
        String countDownDay = null;
        if (countDown != BirthItem.UN_KNOWN_I) {
            countDownDay = countDown + " 天";
        }

        helper.setText(R.id.tv_name, item.name)
                //年龄
                .setText(R.id.tv_age, "年龄: " + item.getAge())
                //生肖
                .setText(R.id.tv_chinese_zodiac, "生肖: " + getNoNullStr(item.chineseZodiac))
                //星座
                .setText(R.id.tv_zodiac, item.zodiac)
                //旧历
                .setText(R.id.tv_lunar_birthday, lunarCalendar)
                //新历
                .setText(R.id.tv_birthday, birthday)
                //下次生日
                .setText(R.id.tv_next_bir_date, nextSolarBirthdayStr)
                //倒计时/天
                .setText(R.id.tv_count_down, countDownDay)
                .getView(R.id.iv_sex).setSelected(item.gender);
    }

    private void showRemars(int position) {
        String remarks = getItem(position).remarks;
        if (!TextUtils.isEmpty(remarks)) {
            ToastUtils.showShort(remarks);
        }
    }

    private String getNoNullStr(String str) {
        return TextUtils2.getNoNullString(str);
    }
}
