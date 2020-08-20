package com.xk.android.global;


import com.xk.android.adb.AdbCommand;
import com.xk.android.constants.Constants;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OperationUtils {
    /**
     * 检查设备是否连接
     *
     * @return
     */
    public static ServerResponse checkDeviceAttach() {
        boolean res = AdbCommand.isAttached();
        if (res) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("请检查手机连接状况！");
    }

    /**
     * 获取手机屏幕分辨率
     * @return
     */
    public static ServerResponse getPhoneSize(){
        String phoneSize = AdbCommand.getPhoneSize();
        if (phoneSize.contains(Constants.PHYSICAL_SIZE)) {
            String res = phoneSize.replace(Constants.PHYSICAL_SIZE, "");
            return ServerResponse.createBySuccess(res);
        }
        return ServerResponse.createByErrorMessage("获取手机屏幕分辨率失败");
    }

    /**
     * 截取手机当前屏幕的XML文件
     *
     * @return 成功返回true, 失败返回false
     */
    private static boolean currentWindowXmlShot() {
        String res = AdbCommand.getCurrentWindowXml();
        if (Constants.GET_XML_FILE.equals(res)) {
            return true;
        }
        return false;
    }

    /**
     * 截取手机当前屏幕XML文件，并将文件提取到Constants.PATH_PC_TEMP指定的pc路径下
     *
     * @return
     */
    public static ServerResponse getCurrentWindowXml() {
        if (currentWindowXmlShot()) {
            AdbCommand.pullFile(Constants.PATH_XML_PHONE, Constants.PATH_PC_TEMP);
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("截取XML文件失败！");
    }


    /**
     * 解析bounds
     *
     * @param source bounds
     * @return 返回解析后获取的xy坐标
     */
    private static HashMap<String, String> getXyFromBounds(String source) {
        //如果source不是以"[]"包裹的字符串，直接返回null
        if (!source.startsWith("[") || !source.endsWith("]")) {
            return null;
        }

        int mid = source.indexOf("]");
        String firstSource = source.substring(1, mid);
        int end = source.length();
        String secondSource = source.substring(mid + 2, end - 1);

        int splitIndex = firstSource.indexOf(",");
        String xStr1 = firstSource.substring(0, splitIndex);
        String yStr1 = firstSource.substring(splitIndex + 1);

        splitIndex = secondSource.indexOf(",");
        String xStr2 = secondSource.substring(0, splitIndex);
        String yStr2 = secondSource.substring(splitIndex + 1);

        int x1 = Integer.parseInt(xStr1);
        int x = x1 + (Integer.parseInt(xStr2) - x1) / 2;

        int y1 = Integer.parseInt(yStr1);
        int y = y1 + (Integer.parseInt(yStr2) - y1) / 2;

        HashMap<String, String> xy = new HashMap<String, String>();
        xy.put("x", String.valueOf(x));
        xy.put("y", String.valueOf(y));
        return xy;
    }

    /**
     * 根据node节点获取xy坐标，并获取text文本值
     * @param sNode 给出的节点
     * @return
     */
    public static ServerResponse<HashMap<String,String>> getXyCoordinateByNode(Node sNode){
        Element node = (Element) sNode;
        if (node == null) {
            return ServerResponse.createByErrorMessage("未找到对应文本的node,请检查文本内容是否正确！");
        }
        //获取node的bounds属性
        String bounds = node.attributeValue("bounds");
        if (bounds == null) {
            return ServerResponse.createByErrorMessage("未找到节点对应的bounds属性，请检查节点是否正确！");
        }

        HashMap<String,String> xyCoordinate =getXyFromBounds(bounds);
        if (xyCoordinate == null) {
            return ServerResponse.createByErrorMessage("请检查bounds的值是否符合规范！");
        }
        String textVal = node.attributeValue("text");
        xyCoordinate.put("text", textVal);
        return ServerResponse.createBySuccess(xyCoordinate);
    }

    /**
     * 解析xml文件，获取对应单个满足要求属性节点的坐标
     * @param  attrName 属性名称
     * @param attrValue 属性值
     * @return 获取成功返回值中包含一个hash函数，key为x和y,value为对应坐标
     */
    public static ServerResponse<HashMap<String, String>> getSingleXyCoordinate(String attrName,String attrValue) {
        Element root = null;
        try {
            root = XmlParse.getRootElement(Constants.PATH_XML_PC);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (root == null) {
            return ServerResponse.createByErrorMessage("解析xml文件失败！");
        }
        String xPath = "//node[@" + attrName + "='" + attrValue + "']";
        return getXyCoordinateByNode(XmlParse.getSingleNodeByXpath(root, xPath));
    }

    /**
     * 解析xml文件，获取对应多个满足要求属性节点的坐标
     * @param  attrName 属性名称
     * @param attrValue 属性值
     * @return 获取成功返回值中包含一个hash函数，key为x和y,value为对应坐标，若没找到也返回成功，只不过数据为空
     */
    public static ServerResponse<List<HashMap<String,String>>> getXyCoordinates(String attrName,String attrValue) {
        Element root = null;
        try {
            root = XmlParse.getRootElement(Constants.PATH_XML_PC);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (root == null) {
            return ServerResponse.createByErrorMessage("解析xml文件失败！");
        }
        String xPath = "//node[@" + attrName + "='" + attrValue + "']";
        List<Node> nodes = XmlParse.getNodesByPath(root, xPath);
        if (nodes == null || nodes.size() == 0) {
            return ServerResponse.createBySuccess();
        }
        List<HashMap<String, String>> xyLists = new ArrayList<HashMap<String, String>>();
        for (Node node : nodes) {
            ServerResponse res = getXyCoordinateByNode(node);
            if (res.isSuccess()) {
                xyLists.add((HashMap<String, String>)res.getData());
            }else {
                return res;
            }
        }
        return ServerResponse.createBySuccess(xyLists);
    }

    /**
     * 点击对应名称
     * @param attrName  xml数据属性，默认为'text'
     * @param attrValue 需要点击的值，例如'钉钉'
     * @return
     */
    public static ServerResponse clickByName(String attrName,String attrValue){
        //截取当前屏幕
        ServerResponse res = OperationUtils.getCurrentWindowXml();
        if(!res.isSuccess()){
            return res;
        }
        res = OperationUtils.getSingleXyCoordinate(attrName,attrValue);
        if(!res.isSuccess()){
            return res;
        }
        HashMap<String, String> xy = (HashMap<String, String>)res.getData();
        AdbCommand.clickScreen(xy.get("x"), xy.get("y"));
        return ServerResponse.createBySuccess();
    }

    /**
     * 实现向右翻页
     * @param x
     * @param y
     */
    public static void pageRight(String x,String y) {
        AdbCommand.swipeScreen(x, y, "0", y);
    }

    /**
     * 实现向下翻页(模拟向上滑动)
     * @param x
     * @param y
     */
    public static void pageDown(String x,String y) {
        AdbCommand.swipeScreen(x, y, x, "0");
    }


}
