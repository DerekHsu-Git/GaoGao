package com.arbo.lib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * SharedPreferences 工具
 */
public class SharePreUtil {

    /**
     * 保存信息到SharedPreferences
     *
     * @param mContext
     * @param sharName
     * @param map
     */
    public static void saveSharePreferens(Context mContext, String sharName,
                                          Map<String, String> map) {
        SharedPreferences spf = mContext.getSharedPreferences(sharName,
                Context.MODE_PRIVATE);
        Editor editor = spf.edit();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());

        }
        editor.commit();
    }


    /**
     * 获取SharedPreferences保存的值
     *
     * @param sharName 保存的文件名
     * @param keyName  键
     * @param mContext
     * @return
     */
    public static String getSharePreferens(Context mContext, String sharName,
                                           String keyName) {
        SharedPreferences sp = mContext.getSharedPreferences(sharName,
                Context.MODE_PRIVATE);
        return sp.getString(keyName, "");
    }

    /**
     * 删除SharePreferens文件
     *
     * @param mContext
     * @param sharName 保存的文件名
     */
    public static void delSharePreferens(Context mContext, String sharName) {
        SharedPreferences sp = mContext.getSharedPreferences(sharName,
                Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    /**
     * 删除SharedPreferences保存的值
     *
     * @param mContext
     * @param sharName
     * @param keyName
     */
    public static void delSharePreferens(Context mContext, String sharName,
                                         String keyName) {
        SharedPreferences sp = mContext.getSharedPreferences(sharName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(keyName);
        editor.commit();
    }

    /**
     * 判断是否包含某个值
     *
     * @param mContext
     * @param sharName
     * @param keyName
     * @return
     */
    public static boolean hasValue(Context mContext, String sharName,
                                   String keyName) {
        // TODO Auto-generated method stub
        SharedPreferences sp = mContext.getSharedPreferences(sharName,
                Context.MODE_PRIVATE);
        boolean ishave = sp.contains(keyName);
        return ishave;
    }

}
