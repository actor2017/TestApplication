package com.actor.testapplication.bean;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.actor.other_utils.BirthdayUtils;
import com.actor.testapplication.utils.String2DateConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

/**
 * description: 生日Item
 *
 * @author : ldf
 * date       : 2021/6/23 on 22
 * @version 1.0
 * <p>
 * TODO: 2021/6/25 生成的Dao会使用get/set方法, 如果generateGettersSetters = false, dao会报错...
 */
@Keep
@Entity(nameInDb = "birthday", createInDb = false, generateGettersSetters = true,
        generateConstructors = false)
public class BirthItem {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    public Long id;

    @Property(nameInDb = "name")
    public String name;

    //true: 男, false: 女
    @Property(nameInDb = "gender")
    public boolean gender;

    /**
     * 农历
     * ∵农历的2月有30号, 而Date的2月没有30号, 甚至没有29号.
     *   如果遇到这种情况, 会转换成3月x号, 造成错误.
     * ∴用String类型
     */
    @Property(nameInDb = "lunar_calendar")
    public String lunarCalendar;

    /**
     * 生日的这个月是不是闰月? 比如阳历 2001-05-04 和 2001-06-03 的农历都是 2001-04-12,
     * 但第2个阳历是"闰四月十二"
     */
    @Property(nameInDb = "is_leap_month")
    public boolean isLeapMonth;

    //当年阳历
    @Property(nameInDb = "solar_calendar")
    @Convert(converter = String2DateConverter.class, columnType = String.class)
    public Date solarCalendar;

    //生日是否过农历. 0否, 1是, 2都过, 3未知
    @Property(nameInDb = "is_birth_celebrate_lunar")
    public int isBirthCelebrateLunar;

    //生肖
    @Property(nameInDb = "chinese_zodiac")
    public String chineseZodiac;

    //星座
    @Property(nameInDb = "zodiac")
    public String zodiac;

    //备注
    @Property(nameInDb = "remarks")
    public String remarks;

    //未知
    @Transient
    public static final int UN_KNOWN_I = -1;

    //还未计算
    @Transient
    private static final int UN_CALCULATE_I = -2;

    //还未计算
    @Transient
    public static final Date UN_CALCULATE_DATE = new Date();

    //阳历岁数
    @Transient
    private int age = UN_CALCULATE_I;

    //农历年月日
    @Transient
    private int lunarYear = UN_CALCULATE_I, lunarMonth, lunarDay;

    //下一个未过的阳历生日
    @Transient
    private Date nextSolarBirthday = UN_CALCULATE_DATE;

    //生日倒数天数
    @Transient
    private long coundDownDay = UN_CALCULATE_I;

    public BirthItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLunarCalendar() {
        return this.lunarCalendar;
    }

    public void setLunarCalendar(String lunarCalendar) {
        this.lunarCalendar = lunarCalendar;
    }

    public boolean getIsLeapMonth() {
        return this.isLeapMonth;
    }

    public void setIsLeapMonth(boolean isLeapMonth) {
        this.isLeapMonth = isLeapMonth;
    }

    public Date getSolarCalendar() {
        return this.solarCalendar;
    }

    public void setSolarCalendar(Date solarCalendar) {
        this.solarCalendar = solarCalendar;
    }

    public int getIsBirthCelebrateLunar() {
        return this.isBirthCelebrateLunar;
    }

    public void setIsBirthCelebrateLunar(int isBirthCelebrateLunar) {
        this.isBirthCelebrateLunar = isBirthCelebrateLunar;
    }

    public String getChineseZodiac() {
        return this.chineseZodiac;
    }

    public void setChineseZodiac(String chineseZodiac) {
        this.chineseZodiac = chineseZodiac;
    }

    public String getZodiac() {
        return this.zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 阳历岁数
     * @return -1表示无效
     */
    public int getAge() {
        if (age == UN_CALCULATE_I) {
            if (lunarCalendar != null) {
                String ymd = lunarCalendar.split(" ")[0];
                String[] split = ymd.split("-");
                int lunarYear = Integer.parseInt(split[0]);
                int lunarMonth = Integer.parseInt(split[1]);
                int lunarDay = Integer.parseInt(split[2]);
                return age = BirthdayUtils.getAgeByLunar(lunarYear, lunarMonth, lunarDay, isLeapMonth);
            } else if (solarCalendar != null) {
                return age = BirthdayUtils.getAgeBySolar(solarCalendar);
            } else {
                return age = UN_KNOWN_I;
            }
        }
        return age;
    }

    /**
     * 下一个未过的阳历生日
     * @return 下一个还未到的阳历生日
     */
    @Nullable
    public Date getNextSolarBirthday() {
        if (nextSolarBirthday == UN_CALCULATE_DATE) {
            //if过农历生日
            if (isBirthCelebrateLunar == 1) {

            } else {
                //过阳历 or 未知
            }
            //如果有农历生日
            if (lunarCalendar != null) {
                String ymd = lunarCalendar.split(" ")[0];
                String[] lunar = ymd.split("-");
                //农历 年 月 日
                int lunarYear = Integer.parseInt(lunar[0]);
                int lunarMonth = Integer.parseInt(lunar[1]);
                int lunDay = Integer.parseInt(lunar[2]);
                nextSolarBirthday = BirthdayUtils.getNextSolarBirthdayByLunar(lunarMonth, lunDay, isLeapMonth);
            } else if (solarCalendar != null) {
                //否则算阳历生日
                nextSolarBirthday = BirthdayUtils.getNextSolarBirthdayBySolar(solarCalendar);
            } else {
                //如果2个都为空
                nextSolarBirthday = null;
            }
        }
        return nextSolarBirthday;
    }

    /**
     * 获取生日倒数天数
     * @return -1表示无效
     */
    public long getCoundDownDay() {
        if (coundDownDay == UN_CALCULATE_I) {
            Date nextSolarBirthday = getNextSolarBirthday();
            if (nextSolarBirthday != null) {
                return coundDownDay = BirthdayUtils.getBirthSpanByNow(nextSolarBirthday);
            } else {
                coundDownDay = UN_KNOWN_I;
            }
        }
        return coundDownDay;
    }
}
