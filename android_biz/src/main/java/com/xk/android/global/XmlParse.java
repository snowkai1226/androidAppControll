package com.xk.android.global;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.util.List;

public class XmlParse {
    /**
     * 读取xml文件并获取根节点
     *
     * @param xmlPath xml文件路径
     * @return 返回根节点
     * @throws DocumentException 抛出document异常
     */
    public static Element getRootElement(String xmlPath) throws DocumentException {
        //创建xml解析器
        SAXReader saxReader = new SAXReader();
        //加载文件，读取到doc中
        Document doc = saxReader.read(xmlPath);
        //通过doc对象获取根元素
        return doc.getRootElement();
    }

    /**
     * 根据xpath获取指定单个Node
     * @param rootEle 根节点
     * @param xPath  xpath表达式
     * @return  根据表达式找到的节点，如果为null表示没找到
     */
    public static Node getSingleNodeByXpath(Element rootEle, String xPath) {
        return rootEle.selectSingleNode(xPath);
    }

    /**
     * 根据xpath获取所有满足要求的Node
     * @param rootEle 根节点
     * @param xPath xpath表达式
     * @return  根据表达式找到的节点集合
     */
    public static List<Node> getNodesByPath(Element rootEle, String xPath) {
        return rootEle.selectNodes(xPath);
    }
}
