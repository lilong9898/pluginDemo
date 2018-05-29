package com.lilong.plugindemo.plugin;

import android.text.TextUtils;

import java.lang.reflect.Field;

public class Tools {

    public static Object getField(Object paramClass, String paramString) {
        if (paramClass == null) return null;
        Field field = null;
        Object object = null;
        Class cl = paramClass.getClass();
        for (; field == null && cl != null; ) {
            try {
                field = cl.getDeclaredField(paramString);
                if (field != null) {
                    field.setAccessible(true);
                }
            } catch (Exception e) {

            }
            if (field == null) {
                cl = cl.getSuperclass();
            }
        }
        try {
            if (field != null)
                object = field.get(paramClass);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void setField(Object paramClass, String paramString,
                                Object newClass) {
        if (paramClass == null || TextUtils.isEmpty(paramString)) return;
        Field field = null;
        Class cl = paramClass.getClass();
        for (; field == null && cl != null; ) {
            try {
                field = cl.getDeclaredField(paramString);
                if (field != null) {
                    field.setAccessible(true);
                }
            } catch (Throwable e) {

            }
            if (field == null) {
                cl = cl.getSuperclass();
            }
        }
        if (field != null) {
            try {
                field.set(paramClass, newClass);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            System.err.print(paramString + " is not found in " + paramClass.getClass().getName());
        }
        return;
    }
}
