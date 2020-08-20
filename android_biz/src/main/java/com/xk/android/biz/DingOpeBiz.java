package com.xk.android.biz;

import com.xk.android.entity.ClockTimeInfo;
import com.xk.android.global.ServerResponse;

public interface DingOpeBiz {

    void setClockTime(ClockTimeInfo clockTime);

    ClockTimeInfo getClockTime();

    ServerResponse autoClockProgram();

}
