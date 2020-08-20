package com.xk.android.adb;

public class CommandStr {
    public static final String CHECK_DEVICES = "adb devices"; //检查连接的设备
    public static final String GET_CURRENT_APP = "adb shell dumpsys window windows | grep 'Current'"; //查询当前页面的activity
    public static final String GET_CURRENT_SCREEN = "adb shell uiautomator dump"; //获取当前屏幕的XML文件
    public static final String PULL_FILE = "adb pull"; //提取
    public static final String OPEN_APP = "adb shell am start -n"; //打开
    public static final String CLOSE_APP = "adb shell am force-stop"; //关闭
    public static final String CLICK_SCREEN = "adb shell input tap";//模拟点击
    public static final String SWIPE_SCREEN = "adb shell input swipe";//模拟滑动
    public static final String GET_PHONE_SIZE = "adb shell wm size";//获取屏幕分辨率
    public static final String GET_PHONE_MODEL = "adb shell getprop ro.product.model"; //获取手机型号
    public static final String GET_PHONE_VERSION = "adb shell getprop ro.build.version.release"; //获取手机版本
    public static final String CHECK_PHONE_ROOT = "adb root"; //查询root
    public static final String SCREEN_AWAKE = "adb shell dumpsys window policy | grep 'mScreenOnEarly'"; //查询手机屏幕唤醒状态
    public static final String PHONE_LOCK = "adb shell dumpsys window policy | grep 'isStatusBarKeyguard'"; //判断手机是否锁住
    public static final String WAKE_UP_PHONE = "adb shell input keyevent 26"; //唤醒手机

}
