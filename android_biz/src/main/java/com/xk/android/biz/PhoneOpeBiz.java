package com.xk.android.biz;

import com.xk.android.entity.PhoneInfo;
import com.xk.android.global.ServerResponse;

public interface PhoneOpeBiz {

    ServerResponse checkPhoneConnection();

    ServerResponse<PhoneInfo> getPhoneInfo();

    ServerResponse unlockAwakePhone(String x, String y);
}
