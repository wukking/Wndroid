package com.wuyson.common.util;

import android.content.res.Resources;

public class DisplayHelper {
    /**
     * 屏幕密度,系统源码注释不推荐使用
     */
    public static final float DENSITY = Resources.getSystem()
            .getDisplayMetrics().density;
}
