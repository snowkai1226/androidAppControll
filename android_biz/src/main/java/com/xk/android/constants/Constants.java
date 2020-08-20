package com.xk.android.constants;

public class Constants {
    //设备连接的相关参数
    public static final String NO_ATTACHED = "List of devices attached ";//未连接成功返回的命令结果
    public static final String START_ATTACHED = "List of devices attached"; //连接成功开头
    public static final String END_ATTACHED = "device"; //连接成功结尾

    public static final String NOT_ROOT = "adbd cannot run as root"; //手机没有root
    public static final String ROOT = "restarting adbd as root"; //手机root

    //xml相关参数
    public static final String GET_XML_FILE = "UI hierchary dumped to: /sdcard/window_dump.xml";//执行获取XML命令成功的返回结果
    public static final String PATH_PC_TEMP = ""; //提取到pc端的文件路径
    public static final String PATH_XML_PHONE = "/sdcard/window_dump.xml"; //xml在手机中的地址
    public static final String PATH_XML_PC = PATH_PC_TEMP + "window_dump.xml"; //xml在PC中的地址
    public static final String ATTR_NAME = "text";

    //手机相关参数
    public static final String PHYSICAL_SIZE = "Physical size: "; //屏幕分辨率开头信息

    //手机锁屏与唤醒参数
    public static final String PHONE_SLEEP = "mAwake=false";
    public static final String PHONE_AWAKE = "mAwake=true";
    public static final String PHONE_LOCKED = "isStatusBarKeyguard=true"; //手机锁屏
    public static final String PHONE_NOT_LOCKED = "isStatusBarKeyguard=false";//手机未锁屏

    public static final int PHONE_LOCKED_FLAG = 1;
    public static final int PHONE_UNLOCKED_FLAG = 0;

    public static final int PHONE_AWAKE_FLAG = 1;
    public static final int PHONE_SLEEP_FLAG = 0;

    public static final int ERROR = -1;

}
