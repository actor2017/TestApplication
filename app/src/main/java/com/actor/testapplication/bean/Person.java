package com.actor.testapplication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity {

    public String name;
    public int age;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }
}