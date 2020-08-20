package com.xk.android.constants;

public class DingConstants {
    //钉钉相关参数
    public static final String DING_PACKAGE_NAME = "com.alibaba.android.rimet"; //钉钉包名
    public static final String DING_NAME = "com.alibaba.android.rimet/com.alibaba.android.rimet.biz.LaunchHomeActivity"; //钉钉入口
    public static final String DING_OPEN_SUCCESS = "Starting: Intent { cmp=com.alibaba.android.rimet/.biz.LaunchHomeActivity }"; //打开成功标志
    public static final String HOME_ACTIVITY = "com.alibaba.android.rimet/com.alibaba.android.rimet.biz.LaunchHomeActivity"; //主界面名称
    public static final String CLOCK_ACTIVITY = "com.alibaba.android.rimet/com.alibaba.lightapp.runtime.ariver.TheOneActivityMainTask"; //打卡界面
    //工作台相关参数
    public static final String WORK_BENCH_NAME = "content-desc";
    public static final String WORK_BENCH_VALUE = "工作台";

    //考勤打卡相关参数
    public static final String CLOCK_NAME = "text";
    public static final String CLOCK_VALUE = "考勤打卡";

    //上班打卡相关参数
    public static final String ON_DUTY_NAME = "text";
    public static final String ON_DUTY_VALUE = "上班打卡";
    //下班打卡相关参数
    public static final String OFF_DUTY_NAME = "text";
    public static final String OFF_DUTY_VALUE = "下班打卡";

    //时间相关参数
    public static final int ON_DUTY_FLAG = 1;
    public static final int OFF_DUTY_FLAG = 2;
    public static final String AM_ON_DUTY_DESC = "上午上班时间";
    public static final String AM_OFF_DUTY_DESC = "上午下班时间";
    public static final String PM_ON_DUTY_DESC = "下午上班时间";
    public static final String PM_OFF_DUTY_DESC = "下午下班时间";

    //打卡类型
    public static final int AM_ON_DUTY_TYPE = 1; //上午上班打卡
    public static final int AM_OFF_DUTY_TYPE = 2; //上午下班打卡
    public static final int PM_ON_DUTY_TYPE = 3;
    public static final int PM_OFF_DUTY_TYPE = 4;

    //打卡时间
    public static String AM_ON_DUTY_TIME = "7:50:00";
    public static String AM_OFF_DUTY_TIME = "12:11:00";
    public static String PM_ON_DUTY_TIME = "13:35:00";
    public static String PM_OFF_DUTY_TIME = "19:00:00";

    public static void setAmOnDutyTime(String amOnDutyTime) {
        AM_ON_DUTY_TIME = amOnDutyTime;
    }

    public static void setAmOffDutyTime(String amOffDutyTime) {
        AM_OFF_DUTY_TIME = amOffDutyTime;
    }

    public static void setPmOnDutyTime(String pmOnDutyTime) {
        PM_ON_DUTY_TIME = pmOnDutyTime;
    }

    public static void setPmOffDutyTime(String pmOffDutyTime) {
        PM_OFF_DUTY_TIME = pmOffDutyTime;
    }
}
