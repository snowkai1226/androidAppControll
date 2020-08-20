package com.xk.android.entity;

import com.xk.android.constants.DingConstants;
import com.xk.android.global.TimeOpe;

public class DutyTime {
    private int flag; //上下班标记, 1表示上班，2表示下班
    private String desc; //描述
    private String time; //时间

    public DutyTime(int flag, String desc, String time) {
        this.flag = flag;
        this.desc = desc;
        this.time = time;
    }

    /**
     * 根据传入参数类型生成实体类
     * @param type
     * @param day 年月日
     */
    public DutyTime(int type, String day) {
        switch (type) {
            case DingConstants.AM_ON_DUTY_TYPE:
                this.flag = DingConstants.ON_DUTY_FLAG;
                this.desc = DingConstants.AM_ON_DUTY_DESC;
                this.time = TimeOpe.jointDateTime(day,DingConstants.AM_ON_DUTY_TIME);
                break;
            case DingConstants.AM_OFF_DUTY_TYPE:
                this.flag = DingConstants.OFF_DUTY_FLAG;
                this.desc = DingConstants.AM_OFF_DUTY_DESC;
                this.time = TimeOpe.jointDateTime(day,DingConstants.AM_OFF_DUTY_TIME);
                break;
            case DingConstants.PM_ON_DUTY_TYPE:
                this.flag = DingConstants.ON_DUTY_FLAG;
                this.desc = DingConstants.PM_ON_DUTY_DESC;
                this.time = TimeOpe.jointDateTime(day,DingConstants.PM_ON_DUTY_TIME);
                break;
            case DingConstants.PM_OFF_DUTY_TYPE:
                this.flag = DingConstants.OFF_DUTY_FLAG;
                this.desc = DingConstants.PM_OFF_DUTY_DESC;
                this.time = TimeOpe.jointDateTime(day,DingConstants.PM_OFF_DUTY_TIME);
                break;
        }
    }

    public DutyTime(DutyTime dutyTime) {
        this.flag = dutyTime.flag;
        this.desc = dutyTime.desc;
        this.time = dutyTime.time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
