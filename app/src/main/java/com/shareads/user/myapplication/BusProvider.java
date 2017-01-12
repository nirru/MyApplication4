package com.shareads.user.myapplication;

import com.squareup.otto.Bus;

/**
 * Created by user on 11/25/2016.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
