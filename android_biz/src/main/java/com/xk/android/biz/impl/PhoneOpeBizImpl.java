package com.xk.android.biz.impl;


import com.xk.android.adb.AdbCommand;
import com.xk.android.biz.PhoneOpeBiz;
import com.xk.android.constants.Constants;
import com.xk.android.entity.PhoneInfo;
import com.xk.android.global.OperationUtils;
import com.xk.android.global.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("phoneOpeBiz")
public class PhoneOpeBizImpl implements PhoneOpeBiz {

    //检查手机连接情况
    public ServerResponse checkPhoneConnection() {
        return OperationUtils.checkDeviceAttach();
    }

    private String checkRoot() {
        String res = AdbCommand.checkRoot();
        if (res.contains(Constants.NOT_ROOT)) {
            return "否";
        } else if (res.contains(Constants.ROOT)) {
            return "是";
        } else {
            return "未知";
        }
    }

    /**
     * 根据分辨率计算中心点坐标
     * @param size
     * @return
     */
    private List<String> calCenterXy(String size) {
        int index = size.indexOf("x");
        Integer x = Integer.parseInt(size.substring(0, index));
        Integer y = Integer.parseInt(size.substring(index + 1));
        x = x / 2;
        y = y / 2;
        List<String> centerXy = new ArrayList<String>();
        centerXy.add(x.toString());
        centerXy.add(y.toString());
        return centerXy;
    }


    /**
     * 获取手机分辨率
     * @return
     */
    private String getPhoneSize(){
        String phoneSize = AdbCommand.getPhoneSize();
        if (phoneSize.contains(Constants.PHYSICAL_SIZE)) {
            String res = phoneSize.replace(Constants.PHYSICAL_SIZE, "");
            return res;
        }
        return null;
    }

    //获取手机基本信息
    public ServerResponse<PhoneInfo> getPhoneInfo() {
        //检查手机连接情况
        ServerResponse res = checkPhoneConnection();
        if (!res.isSuccess()) {
            return res;
        }
        PhoneInfo phoneInfo = new PhoneInfo();
        String model = AdbCommand.getPhoneModel();
        String version = AdbCommand.getPhoneVersion();
        String root = checkRoot();
        String size = getPhoneSize();
        List<String> xy = calCenterXy(size);
        phoneInfo.setModel(model);
        phoneInfo.setVersion(version);
        phoneInfo.setRoot(root);
        phoneInfo.setSize(size);
        phoneInfo.setCenterX(xy.get(0));
        phoneInfo.setCenterY(xy.get(1));
        return ServerResponse.createBySuccess(phoneInfo);
    }



    /**
     * 保证手机处于解锁并唤醒状态
     * @return
     */
    public ServerResponse unlockAwakePhone(String x,String y){
        if (AdbCommand.isLocked() == Constants.PHONE_UNLOCKED_FLAG && AdbCommand.isAwake() == Constants.PHONE_AWAKE_FLAG) {
            return ServerResponse.createBySuccess();
        }
        if (AdbCommand.isAwake() == Constants.PHONE_SLEEP_FLAG) { //处于休眠状态
            AdbCommand.awakePhone();//唤醒手机
            if(AdbCommand.isLocked() == Constants.PHONE_LOCKED_FLAG){//处于锁定状态
                OperationUtils.pageDown(x, y); //向上滑动手机解锁
            }
        }
        return ServerResponse.createBySuccess();
    }
}
