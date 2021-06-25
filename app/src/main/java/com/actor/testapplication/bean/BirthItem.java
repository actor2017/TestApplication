package com.actor.testapplication.bean;

import androidx.annotation.Keep;

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

    @Id
    @Property(nameInDb = "id")
    public Long id;

    @Property(nameInDb = "name")
    public String name;

    @Property(nameInDb = "gender")
    public boolean gender;

    //阴历
    @Property(nameInDb = "lunar_calendar")
    @Convert(converter = String2DateConverter.class, columnType = String.class)
    public Date lunarCalendar;

    //当年阳历
    @Property(nameInDb = "birthday_date")
    @Convert(converter = String2DateConverter.class, columnType = String.class)
    public Date birthdayDate;

    //当年阳历
    @Property(nameInDb = "birthday_str")
    public String birthday;

    //生肖
    @Property(nameInDb = "chinese_zodiac")
    public String chineseZodiac;

    //星座
    @Property(nameInDb = "zodiac")
    public String zodiac;

    //生日倒数天数
    @Transient
    public int coundDownDay;

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

    public Date getLunarCalendar() {
        return this.lunarCalendar;
    }

    public void setLunarCalendar(Date lunarCalendar) {
        this.lunarCalendar = lunarCalendar;
    }

    public Date getBirthdayDate() {
        return this.birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
}
