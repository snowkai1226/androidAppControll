package com.xk.android.biz.impl;


import com.xk.android.adb.AdbCommand;
import com.xk.android.biz.DingOpeBiz;
import com.xk.android.biz.PhoneOpeBiz;
import com.xk.android.constants.DingConstants;
import com.xk.android.entity.ClockTimeInfo;
import com.xk.android.entity.DutyTime;
import com.xk.android.entity.PhoneInfo;
import com.xk.android.global.OperationUtils;
import com.xk.android.global.ServerResponse;
import com.xk.android.global.TimeOpe;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Service("dingOpeBiz")
public class DingOpeBizImpl implements DingOpeBiz {
    private Queue<DutyTime> timeQueue;

    private PhoneOpeBiz phoneOpeBiz;

    public DingOpeBizImpl() {
        timeQueue = new LinkedList<DutyTime>();
        phoneOpeBiz = new PhoneOpeBizImpl();
    }

    /**
     * 关闭钉钉
     */
    public void closeDing() {
        AdbCommand.closeApp(DingConstants.DING_PACKAGE_NAME);
    }

    /**
     * 打开钉钉
     *
     * @return
     */
    public boolean openDing() {
        closeDing();//确保打开成功，先关闭钉钉
        String res = AdbCommand.openApp(DingConstants.DING_NAME);
        return res.equals(DingConstants.DING_OPEN_SUCCESS);
    }

    /**
     * 检查是否为主界面
     *
     * @return
     */
    public boolean isHomeActivity() {
        return AdbCommand.getCurrentActivity().contains(DingConstants.HOME_ACTIVITY);
    }

    /**
     * 检查是否为打卡界面
     * @return
     */
    public boolean isClockActivity(){
        return AdbCommand.getCurrentActivity().contains(DingConstants.CLOCK_ACTIVITY);
    }

    /**
     * 进入工作台
     *
     * @return
     */
    public ServerResponse enterWorkBench() {
        return OperationUtils.clickByName(DingConstants.WORK_BENCH_NAME, DingConstants.WORK_BENCH_VALUE);
    }

    /**
     * 进入考勤打卡
     *
     * @return
     */
    public ServerResponse enterClock() {
        return OperationUtils.clickByName(DingConstants.CLOCK_NAME, DingConstants.CLOCK_VALUE);
    }

    /**
     * 点击上班打卡
     *
     * @return
     */
    public ServerResponse onDutyClick() {
        return OperationUtils.clickByName(DingConstants.ON_DUTY_NAME, DingConstants.ON_DUTY_VALUE);
    }

    /**
     * 点击下班打卡
     *
     * @return
     */
    public ServerResponse offDutyClick() {
        return OperationUtils.clickByName(DingConstants.OFF_DUTY_NAME, DingConstants.OFF_DUTY_VALUE);
    }

    /**
     * 进入打卡界面
     *
     * @return
     */
    public ServerResponse enterClockActivity() {
        //打开钉钉
        boolean openRes = openDing();
        if (!openRes) {
            return ServerResponse.createByErrorMessage("打开钉钉失败！");
        }
        //循环检查是否为主界面，如果不是，等待1秒
        int maxSeconds = 5; //至多等待maxSeconds秒
        while (!isHomeActivity() && maxSeconds > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
                maxSeconds--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (maxSeconds == 0) {
            return ServerResponse.createByErrorMessage("进入钉钉主界面失败！");
        }
        //进入工作台
        ServerResponse res = enterWorkBench();
        if (!res.isSuccess()) {
            return res;
        }
        OperationUtils.getCurrentWindowXml(); //如果没有该行代码，下行获取的是错误的xml，原因未知
        res = enterClock();//点击进入打卡界面
        if (!res.isSuccess()) {
            return res;
        }
        //等待若干秒时间
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean flag = isClockActivity();//检查是否进入打开界面
        if (flag){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByErrorMessage("进入打卡界面失败");
        }
    }

    /**
     * 上班打卡签到
     *
     * @return
     */
    public ServerResponse onDutyClock() {
        ServerResponse res;
        res = enterClockActivity();
        if (!res.isSuccess()) {
            return res;
        }

        res = onDutyClick();
        return res;
    }

    /**
     * 下班打卡签到
     *
     * @return
     */
    public ServerResponse offDutyClock() {
        ServerResponse res;
        res = enterClockActivity();
        if (!res.isSuccess()) {
            return res;
        }
        res = offDutyClick();
        return res;
    }

    /*..................................................自动打卡时间相关.....................................................*/

    /**
     * 设置打卡时间
     * @param clockTime
     */
    public void setClockTime(ClockTimeInfo clockTime) {
        DingConstants.setAmOnDutyTime(clockTime.getMorningTime());
        DingConstants.setAmOffDutyTime(clockTime.getMorningClosingTime());
        DingConstants.setPmOnDutyTime(clockTime.getAfternoonTime());
        DingConstants.setPmOffDutyTime(clockTime.getAfternoonClosingTime());
    }

    /**
     * 获取打卡时间
     * @return
     */
    public ClockTimeInfo getClockTime(){
        ClockTimeInfo clockTimeInfo = new ClockTimeInfo();
        clockTimeInfo.setMorningTime(DingConstants.AM_ON_DUTY_TIME);
        clockTimeInfo.setMorningClosingTime(DingConstants.AM_OFF_DUTY_TIME);
        clockTimeInfo.setAfternoonTime(DingConstants.PM_ON_DUTY_TIME);
        clockTimeInfo.setAfternoonClosingTime(DingConstants.PM_OFF_DUTY_TIME);
        return clockTimeInfo;
    }
    /**
     * 将指定日期的打卡时间加入到队列中
     *
     * @param day 当前年月日
     * @return 成功返回true
     */
    private boolean addOneDay(String day) {
        DutyTime dutyTime1 = new DutyTime(DingConstants.AM_ON_DUTY_TYPE, day);
        DutyTime dutyTime2 = new DutyTime(DingConstants.AM_OFF_DUTY_TYPE, day);
        DutyTime dutyTime3 = new DutyTime(DingConstants.PM_ON_DUTY_TYPE, day);
        DutyTime dutyTime4 = new DutyTime(DingConstants.PM_OFF_DUTY_TYPE, day);
        boolean res = timeQueue.add(dutyTime1)
                && timeQueue.add(dutyTime2)
                && timeQueue.add(dutyTime3)
                && timeQueue.add(dutyTime4);
        return res;
    }

    /**
     * 初始化当前队列，将今天需打卡时间加入到队列中
     *
     * @return
     */
    private boolean initQueue() {
        String day = TimeOpe.getCurDay();
        if (!addOneDay(day)) {
            return false;
        }
        String date = TimeOpe.getCurDate();
        while (!timeQueue.isEmpty()) {
            DutyTime dutyTime = timeQueue.peek();
            if (arriveClockTime(dutyTime)) {//下一个打卡时间已过时
                timeQueue.poll();//剔除掉过时的打卡时间
                addNextDutyTime(dutyTime);//增加下一天的对应打卡时间
            } else { //所有过时的打卡时间均被剔除
                break;
            }
        }
        return true;
    }

    /**
     * 指定日期增加天数
     *
     * @param num  需要增加的天数
     * @param date 指定日期
     * @return 新日期
     * @throws ParseException
     */
    private String increaseDays(int num, String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currDate = null;
        try {
            currDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.add(Calendar.DATE, num);
        currDate = calendar.getTime();
        String newDate = format.format(currDate);
        return newDate;
    }

    /**
     * 将当前打卡时间的下一天打卡时间加入到队列中
     *
     * @param dutyTime
     * @return
     */
    private boolean addNextDutyTime(DutyTime dutyTime) {
        String date = increaseDays(1, dutyTime.getTime());
        DutyTime newDutyTime = new DutyTime(dutyTime);
        newDutyTime.setTime(date);
        return timeQueue.add(newDutyTime);
    }

    /**
     * 判断当前时间是否到达指定打卡时间
     *
     * @param dutyTime 指定打卡时间实例
     * @return true表示到时间
     */
    private boolean arriveClockTime(DutyTime dutyTime) {
        String date = dutyTime.getTime();
        return TimeOpe.arriveTime(date);
    }

    /**
     * 判断当前时间是否为周末
     *
     * @param dutyTime
     * @return true表示是周末
     */
    private boolean isWeekend(DutyTime dutyTime) {
        String date = dutyTime.getTime();
        return TimeOpe.isWeekend(date);
    }

    /**
     * 获取当前时间与待打卡时间的时间差
     *
     * @param dutyTime 待打卡时间
     * @return 时间差（微秒）
     */
    private long getTimesGap(DutyTime dutyTime) {
        String toDate = dutyTime.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String fromDate = format.format(date);
        return TimeOpe.getMicroSecondsGap(fromDate, toDate);
    }

    /*..................................................自动打卡时间相关结束..........................................*/


    /**
     * 自动打卡，不设置打卡天数，程序一直执行
     *
     * @return
     */
    public ServerResponse autoClockProgram() {
        ServerResponse<PhoneInfo> phoneRes = phoneOpeBiz.getPhoneInfo();
        if (!phoneRes.isSuccess()) {
            return phoneRes;
        }
        String x = phoneRes.getData().getCenterX();
        String y = phoneRes.getData().getCenterY();
        initQueue();//初始化打卡队列
        while (!timeQueue.isEmpty()) {
            DutyTime dutyTime = timeQueue.peek();
            //判断打卡时间是否为周末
            if (isWeekend(dutyTime)) {//是周末，则无需打卡
                timeQueue.poll();//当前时间移出队列
                addNextDutyTime(dutyTime); //将下一天打卡时间加入到队列中
            } else {//不是周末
                //判断当前时间是否到达打卡时间
                if (!arriveClockTime(dutyTime)) { //未到达打卡时间
                    long sleepMicros = getTimesGap(dutyTime);//获取与待打卡时间的时间差
                    try {
                        sleep(sleepMicros); //线程休眠，直到到达待打卡时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {//到达待打卡时间
                    timeQueue.poll();//当前时间移出队列
                    addNextDutyTime(dutyTime); //将下一天打卡时间加入到队列中
                    phoneOpeBiz.unlockAwakePhone(x, y); //唤醒并解锁手机
                    int flag = dutyTime.getFlag();
                    if (flag == DingConstants.ON_DUTY_FLAG) {//是上班打卡
                        ServerResponse res = onDutyClock();
                        if (!res.isSuccess()) {
                            System.out.println(res.getMsg());
                        }
                    } else if (flag == DingConstants.OFF_DUTY_FLAG) { //下班打卡
                        ServerResponse res = offDutyClock();
                        if (!res.isSuccess()) {
                            System.out.println(res.getMsg());
                        }
                    } else { //都是不是
                        return ServerResponse.createByErrorMessage("打卡类型错误！");
                    }
                }
            }
        }
        return ServerResponse.createBySuccess();
    }
}
