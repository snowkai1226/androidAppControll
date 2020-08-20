package com.xk.android.controller;

import com.xk.android.biz.DingOpeBiz;
import com.xk.android.biz.PhoneOpeBiz;
import com.xk.android.entity.ClockTimeInfo;
import com.xk.android.global.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("/dingOpeController")
@RequestMapping("/ding")
public class DingOpeController {
    @Autowired
    private DingOpeBiz dingOpeBiz;
    @Autowired
    private PhoneOpeBiz phoneOpeBiz;

    @RequestMapping("/to_update_time")
    public String toUpdateTime(Map<String,Object> map){
        ClockTimeInfo clockTimeInfo = dingOpeBiz.getClockTime();
        map.put("time", clockTimeInfo);
        return "clock_time_update";
    }

    @RequestMapping("/update_time")
    public String updateTime(ClockTimeInfo time){
        dingOpeBiz.setClockTime(time);
        return "redirect:to_clock";
    }

    @RequestMapping("/to_clock")
    public String toClock(Map<String, Object> map) {
        ServerResponse res = phoneOpeBiz.getPhoneInfo();
        if (!res.isSuccess()) {
            String msg = res.getMsg();
            map.put("msg", msg);
            return "err_page";
        }
        ClockTimeInfo clockTimeInfo = dingOpeBiz.getClockTime();
        map.put("time", clockTimeInfo);
        return "clock_confirm";
    }

    @RequestMapping("/start_clock")
    public void startClock() {
        dingOpeBiz.autoClockProgram();
    }

}
