package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.actor.myandroidframework.utils.database.GreenDaoUtils;
import com.actor.myandroidframework.widget.ItemRadioGroupLayout;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.actor.testapplication.R;
import com.actor.testapplication.bean.BirthItem;
import com.actor.testapplication.databinding.ActivityBirthdayDetailBinding;
import com.actor.testapplication.utils.Global;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.greendao.gen.BirthItemDao;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.Calendar;
import java.util.Date;

/**
 * description: 生日新增 & 生日详情&修改
 * company    :
 * @author    : ldf
 * date       : 2023/1/10/0010 on 17:28
 */
public class BirthdayDetailActivity extends BaseActivity<ActivityBirthdayDetailBinding> {

    private ItemTextInputLayout itilName, itilLunarBirthday, itilSolarBirthday, itilRemarks;
    private ItemRadioGroupLayout<String> irglSex, irglIsLeapMonth;

    private static final BirthItemDao        DAO    = GreenDaoUtils.getDaoSession().getBirthItemDao();

    private       TimePickerView pickerView;
    //默认显示的日期
    private final Calendar       showDate = Calendar.getInstance();
    private long id;
    private BirthItem item;
    private String lunarCalendarDate;
    private Date solarCalendarDate;
    private XPopup.Builder confirmBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvTitle = viewBinding.tvTitle;
        TextView tvDelete = viewBinding.tvDelete;
        itilName = viewBinding.itilName;
        irglSex = viewBinding.irglSex;
        itilLunarBirthday = viewBinding.itilLunarBirthday;
        irglIsLeapMonth = viewBinding.irglIsLeapMonth;
        itilSolarBirthday = viewBinding.itilSolarBirthday;
        itilRemarks = viewBinding.itilRemarks;

        id = getIntent().getLongExtra(Global.id, BirthItem.UN_KNOWN_I);
        if (id != BirthItem.UN_KNOWN_I) {
            tvTitle.setText("生日详情&修改");
            item = GreenDaoUtils.queryUnique(DAO, BirthItemDao.Properties.Id.eq(id));
            itilName.setText(item.name);
            irglSex.setCheckedPosition(item.gender ? 0 : 1);
            irglIsLeapMonth.setCheckedPosition(item.isLeapMonth ? 0 : 1);

            //农历生日
            lunarCalendarDate = item.lunarCalendar;
            //农历转换的新历年月日
            int[] ints = null;
            if (!TextUtils.isEmpty(lunarCalendarDate)) {
                String ymd = lunarCalendarDate.split(" ")[0];
                itilLunarBirthday.setText(ymd);
                String[] split = ymd.split("-");
                //农历年月日
                int year = Integer.parseInt(split[0]);
                int month = Integer.parseInt(split[1]);
                int day = Integer.parseInt(split[2]);
                ints = LunarCalendar.lunarToSolar(year, month, day, item.isLeapMonth);
            }
            //当年新历生日
            solarCalendarDate = item.solarCalendar;
            if (solarCalendarDate != null) {
                String date2String = TimeUtils.date2String(solarCalendarDate, "yyyy-MM-dd");
                itilSolarBirthday.setText(date2String);
            }
            itilRemarks.setText(item.remarks);

            //设置显示哪天
            if (solarCalendarDate != null) {
                showDate.setTime(solarCalendarDate);
            } else if (!TextUtils.isEmpty(lunarCalendarDate)) {
                showDate.set(ints[0], ints[1] - 1, ints[2]);
            } else {
                showDate.set(1990, 5, 6);
            }
        } else {
            tvTitle.setText("生日新增");
            tvDelete.setVisibility(View.INVISIBLE);
        }

        setOnClickListeners(R.id.iv_back, R.id.tv_delete, R.id.itil_lunar_birthday,
                R.id.itil_solar_birthday, R.id.btn_done);
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_delete:
                showConfirmPopupView("删除", "确定删除此人吗?", false, new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        GreenDaoUtils.deleteByKey(DAO, id);
                        showConfirmPopupView("删除结果", "已删除成功!", true, new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                setResult(RESULT_OK, new Intent()
                                        .putExtra(Global.id, id)
                                        .putExtra(Global.isDelete, true)
                                );
                                onBackPressed();
                            }
                        });
                    }
                });
                break;
            case R.id.itil_lunar_birthday:
                TimePickerView lunarPickerView = getPickerView();
                lunarPickerView.setTitleText("农历生日");
                lunarPickerView.setLunarCalendar(true);
                lunarPickerView.show();
                break;
            case R.id.itil_solar_birthday:
                TimePickerView solarPickerView = getPickerView();
                solarPickerView.setTitleText("新历生日");
                solarPickerView.setLunarCalendar(false);
                solarPickerView.show();
                break;
            case R.id.btn_done:
                if (isNoEmpty(itilName)) {
                    String name = getText(itilName);
                    if (id != BirthItem.UN_KNOWN_I) {
                        //修改
                        inflateData(item);
                        GreenDaoUtils.update(DAO, item);
                        showConfirmPopupView("修改成功", name + "修改成功!", true, new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                setResult(RESULT_OK, new Intent()
                                        .putExtra(Global.id, id)
                                );
                                onBackPressed();
                            }
                        });
                    } else {
                        //新增
                        BirthItem item = GreenDaoUtils.queryUnique(DAO, BirthItemDao.Properties.Name.eq(name));
                        if (item != null) {
                            showConfirmPopupView("重复存储", name + " 已经存储过了, 请勿重复存储!", true, null);
                        } else {
                            BirthItem item1 = new BirthItem();
                            inflateData(item1);
                            long insertId = GreenDaoUtils.insert(DAO, item1);
                            showConfirmPopupView("新增成功", name + "新增成功!", true, new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    setResult(RESULT_OK, new Intent()
                                            .putExtra(Global.id, insertId)
                                            .putExtra(Global.isInsert, true)
                                    );
                                    onBackPressed();
                                }
                            });
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    //填充数据
    private void inflateData(BirthItem item) {
        String name = getText(itilName);
        String remarks = getText(itilRemarks);
        item.name = name;
        item.gender = irglSex.getCheckedPosition() == 0;
        if (lunarCalendarDate != null) {
            item.lunarCalendar = lunarCalendarDate;
        }
        //出生农历的月份是不是闰月
        item.isLeapMonth = irglIsLeapMonth.getCheckedPosition() == 0;
        if (solarCalendarDate != null) {
            item.solarCalendar = solarCalendarDate;
        }
        //生肖, 星座
        if (solarCalendarDate != null) {
            item.chineseZodiac = TimeUtils.getChineseZodiac(solarCalendarDate);
            item.zodiac = TimeUtils.getZodiac(solarCalendarDate);
        } else if (lunarCalendarDate != null) {
            item.chineseZodiac = TimeUtils.getChineseZodiac(lunarCalendarDate);
            item.zodiac = TimeUtils.getZodiac(lunarCalendarDate);
        }
        item.remarks = remarks;
    }

    private void showConfirmPopupView(CharSequence title, CharSequence content, boolean isHideCancel, OnConfirmListener confirmListener) {
        if (confirmBuilder == null) {
            confirmBuilder = new XPopup.Builder(this)
                    //.maxWidth()
//                    .autoDismiss(true)//操作完毕后是否自动关闭弹窗，默认为true。比如：点击Confirm弹窗的确认按钮默认是关闭弹窗
                    .dismissOnTouchOutside(false)
                    .dismissOnBackPressed(false);
        }
        //ConfirmPopupView 要每次都 asConfirm(new), 否则改不了 标题&内容&divider2(下方竖分割线)的显示隐藏
        ConfirmPopupView confirmPopupView = confirmBuilder.asConfirm(title, content, confirmListener);
        confirmPopupView.isHideCancel = isHideCancel;
        confirmPopupView.show();
    }

    //农历选择器
    private TimePickerView getPickerView() {
        if (pickerView == null) {
            Calendar startDate = Calendar.getInstance();
            startDate.set(1900, 1, 1);
            Calendar endDate = Calendar.getInstance();
            pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    //更新为选中的新历
                    solarCalendarDate = date;
                    if (pickerView.isLunarCalendar()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        int[] ints = LunarCalendar.solarToLunar(year, month, day);

                        /**
                         * 农历庚午(马)年五月十三
                         * @see ChinaDate#calElement(int, int, int) 有bug, 导致下方算出的农历不对
                         */
//                        String lunar = ChinaDate.oneDay(year, month, day);

//                        String months = nStr1[ints[1]];
//                        String chinaDate = ChinaDate.getChinaDate(ints[2]);
//                        String stringFormat = getStringFormat("%d年%s月%s", ints[0], months, chinaDate);

                        String stringFormat = getStringFormat("%d-%02d-%02d", ints[0], ints[1], ints[2]);
                        //更新为选中的农历
                        lunarCalendarDate = stringFormat.concat(" 00:00:00");
                        itilLunarBirthday.setText(stringFormat);
                    } else {
                        String solar = TimeUtils.date2String(date, "yyyy-MM-dd");
                        itilSolarBirthday.setText(solar);
                    }
                }
                // 默认全部显示
            }).setType(new boolean[]{true, true, true, false, false, false})
                    //标题文字
//                    .setTitleText("请选择农历生日")
                    .isCyclic(false)//是否循环滚动
                    .isDialog(true)//是否显示为对话框样式
                    .setRangDate(startDate, endDate)
                    .setDate(showDate)
                    .setLunarCalendar(false)
                    .build();
        }
        return pickerView;
    }
}
