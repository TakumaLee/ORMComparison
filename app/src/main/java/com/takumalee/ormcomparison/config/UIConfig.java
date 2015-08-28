package com.takumalee.ormcomparison.config;

import android.support.design.widget.TabLayout;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by TakumaLee on 15/6/30.
 */
public class UIConfig {
    public static void setupTabSelectorColor(TabLayout tabs, int color) {
        try {
            Field field = TabLayout.class.getDeclaredField("mTabStrip");
            field.setAccessible(true);
            Object value = field.get(tabs);

            Method method = value.getClass().getDeclaredMethod("setSelectedIndicatorColor", Integer.TYPE);
            method.setAccessible(true);
            method.invoke(value, color);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
