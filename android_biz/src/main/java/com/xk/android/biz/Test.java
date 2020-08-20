package com.xk.android.biz;

import com.xk.android.biz.impl.DingOpeBizImpl;
import com.xk.android.biz.impl.PhoneOpeBizImpl;
import com.xk.android.entity.PhoneInfo;
import com.xk.android.global.ServerResponse;

public class Test {
    public static void main(String[] args) {
        DingOpeBiz dingOpeBiz = new DingOpeBizImpl();
        dingOpeBiz.autoClockProgram();
        return;
    }
}
