package com.arbo.gaogao;

/**
 * Created by Administrator on 2017/1/29.
 */

public class Config {

    public static final String DB__IS_READ_NAME = "IsRead";
    public static final String ZHIHU = "zhihu";
    public static final String TOPNEWS= "topnews";
    public static final String MEIZI="meizi";
    public static final int  meiziSize=10;


    public static boolean isNight = false;
    public static boolean isDebug=true;

    public static final String THEME_MODEL = "theme_model";

    /**
     * #TODO 将颜色写入到Color中 2017-2-4 22:16:25
     * 以下为临时写法
     * */
    public static final int DARK_TEXT_COLOR = 0xb3ffffff;
    public static final int DARK_BACKGROUND_COLOR = 0xff333333;
    public static final int DARK_IMAGE_ALPHA = 200;

    public static final int LIGHT_TEXT_COLOR = 0xff000000;
    public static final int LIGHT_BACKGROUND_COLOR = 0xffffffff;
    public static final int LIGHT_IMAGE_ALPHA = 255;

    public static final int APP_BAR_LIGHT_COLOR = 0xbcf40b0b;

}
