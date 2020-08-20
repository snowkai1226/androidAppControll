package com.xk.android.controller;

import com.xk.android.biz.PhoneOpeBiz;
import com.xk.android.entity.PhoneInfo;
import com.xk.android.global.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("phoneOpeController")
@RequestMapping("/phone")
public class PhoneOpeController {
    @Autowired
    private PhoneOpeBiz phoneOpeBiz;

    @RequestMapping("/info")
    public String info(Map<String, Object> map) {
        ServerResponse<PhoneInfo> res = phoneOpeBiz.getPhoneInfo();
        if (!res.isSuccess()) {
            String msg = res.getMsg();
            map.put("msg", msg);
            return "err_page";
        }
        map.put("phoneInfo", res.getData());
        return "info_show";
    }
}
