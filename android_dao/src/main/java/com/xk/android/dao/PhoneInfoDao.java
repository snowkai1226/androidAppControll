package com.xk.android.dao;

import com.xk.android.entity.PhoneInfo;

public interface PhoneInfoDao {
    void insert(PhoneInfo phoneInfo);

    PhoneInfo select(String model);

}
