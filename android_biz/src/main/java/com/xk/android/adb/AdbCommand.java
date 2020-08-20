package com.xk.android.adb;

import com.xk.android.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbCommand {
    /**
     * 执行exe命令
     *
     * @param cmd adb命令
     * @return 执行结果
     */
    private static String exeAdbCommand(String cmd) {
        String result = "";
        BufferedReader in = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 检查已经和adb连接的手机设备
     *
     * @return 返回执行命令的结果
     */
    private static String checkDevices() {
        return exeAdbCommand(CommandStr.CHECK_DEVICES);
    }

    /**
     * 查询电脑锁屏状态
     *
     * @return
     */
    private static String phoneLock() {
        return exeAdbCommand(CommandStr.PHONE_LOCK);
    }

    /**
     * 查询电脑屏幕唤醒状态
     *
     * @return
     */
    private static String phoneWake() {
        return exeAdbCommand(CommandStr.SCREEN_AWAKE);
    }


    /**
     * 检查是否有手机连接
     *
     * @return true说明有手机连接，false说明无手机连接
     */
    public static boolean isAttached() {
        String line = checkDevices();
        String regex = ".*" + Constants.START_ATTACHED + ".*" + Constants.END_ATTACHED + ".*";
        boolean res = line.matches(regex);
        return res;
    }

    /**
     * 检测手机是否锁屏
     *
     * @return 锁屏返回1，未锁屏返回0，发生错误返回-1
     */
    public static int isLocked() {
        String line = phoneLock();
        if (line.contains(Constants.PHONE_LOCKED)) {
            return Constants.PHONE_LOCKED_FLAG;
        } else if (line.contains(Constants.PHONE_NOT_LOCKED)) {
            return Constants.PHONE_UNLOCKED_FLAG;
        }
        return Constants.ERROR;
    }

    /**
     * 检测手机屏幕是否处于唤醒状态
     *
     * @return 唤醒状态返回1，休眠返回0，发生错误返回-1
     */
    public static int isAwake() {
        String line = phoneWake();
        if (line.contains(Constants.PHONE_AWAKE)) {
            return Constants.PHONE_AWAKE_FLAG;
        } else if (line.contains(Constants.PHONE_SLEEP)) {
            return Constants.PHONE_SLEEP_FLAG;
        }
        return Constants.ERROR;
    }

    /**
     * 唤醒手机
     *
     * @return
     */
    public static String awakePhone() {
        return exeAdbCommand(CommandStr.WAKE_UP_PHONE);
    }


    /**
     * 从手机端提取文件到pc端
     *
     * @param phonePath 手机文件路径
     * @param pcPath    pc端文件夹路径
     * @return 命令执行结果
     */

    public static String pullFile(String phonePath, String pcPath) {
        String cmd = CommandStr.PULL_FILE + " " + phonePath + " " + pcPath;
        return exeAdbCommand(cmd);
    }

    /**
     * 打开某个app
     *
     * @param appName 需要打开的app名
     * @return
     */
    public static String openApp(String appName) {
        String cmd = CommandStr.OPEN_APP + " " + appName;
        return exeAdbCommand(cmd);
    }

    /**
     * 关闭某个app
     *
     * @param appName 需要关闭的app名
     * @return
     */
    public static String closeApp(String appName) {
        String cmd = CommandStr.CLOSE_APP + " " + appName;
        return exeAdbCommand(cmd);
    }

    /**
     * 查询当前的activity
     *
     * @return 返回当前的activity名
     */
    public static String getCurrentActivity() {
        return exeAdbCommand(CommandStr.GET_CURRENT_APP);
    }

    /**
     * 获取当前屏幕的xml文件
     *
     * @return
     */
    public static String getCurrentWindowXml() {
        return exeAdbCommand(CommandStr.GET_CURRENT_SCREEN);
    }

    /**
     * 模拟点击屏幕
     *
     * @param x x坐标
     * @param y y坐标
     * @return
     */
    public static String clickScreen(String x, String y) {
        String cmd = CommandStr.CLICK_SCREEN + " " + x + " " + y;
        return exeAdbCommand(cmd);
    }

    /**
     * 模拟点击屏幕
     *
     * @param xy 点的xy坐标
     * @return
     */
    public static String clickScreen(String xy) {
        String cmd = CommandStr.CLICK_SCREEN + " " + xy;
        return exeAdbCommand(cmd);
    }

    /**
     * 模拟滑动
     *
     * @param x1 初始坐标x
     * @param y1 初始坐标y
     * @param x2 终点坐标x
     * @param y2 终点坐标y
     * @return
     */
    public static String swipeScreen(String x1, String y1, String x2, String y2) {
        String cmd = CommandStr.SWIPE_SCREEN + " " + x1 + " " + y1 + " " + x2 + " " + y2;
        return exeAdbCommand(cmd);
    }

    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static String getPhoneSize() {
        return exeAdbCommand(CommandStr.GET_PHONE_SIZE);
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return exeAdbCommand(CommandStr.GET_PHONE_MODEL);
    }

    /**
     * 获取手机版本号
     *
     * @return
     */
    public static String getPhoneVersion() {
        return exeAdbCommand(CommandStr.GET_PHONE_VERSION);
    }

    /**
     * 查询手机是否root
     * @return
     */
    public static String checkRoot() {
        return exeAdbCommand(CommandStr.CHECK_PHONE_ROOT);
    }
}
