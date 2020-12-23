package com.poly.smartfindpro.ui.identification.veriface.internal;

/**
 * Created by vannh.lvt on 10/07/2020
 */
public enum ActionFace {

    portrait(1), turn_left(2), turn_right(3), smile(4), lifed_face(5), face_down(6), blink(8), tilt_head_left(9), tilt_head_right(10), close_eyes(11);

    private int value;

    ActionFace(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
