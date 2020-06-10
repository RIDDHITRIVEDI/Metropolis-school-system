package com.example.riddhi.m1etropolishtutelage;

import android.support.multidex.MultiDexApplication;

/**
 * Created by RIDDHI on 15-04-17.
 */

public class Globalvariable extends MultiDexApplication {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
