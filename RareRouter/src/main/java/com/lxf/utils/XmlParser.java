package com.lxf.utils;

import com.lxf.Process.configure.RareXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {
    private String xmlPath;
    private Document document;
    private Element rareNode;

    public XmlParser(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    private void parse_Rare_appModule() {
        if (rareNode == null) {
            return;
        }
        RareXml.appModule = rareNode.getAttribute("AppModule");
    }

    //用Element方式
    public void element(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            NodeList childNodes = element.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    //获取节点
                    System.out.print(childNodes.item(j).getNodeName() + ":");
                    //获取节点值
                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());

                }
            }
        }
    }

    public void node(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    System.out.print(childNodes.item(j).getNodeName() + ":");
                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());

                }
            }
        }
    }

    public boolean parse() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlPath);
        } catch (Exception e) {
            return false;
        }
        if (document == null) {
            return false;
        }
        rareNode = document.getDocumentElement();
        parse_Rare_appModule();
        return true;
    }

    public String getPkgVal() {
        if (document == null) {
            return null;
        }
        NodeList sList = document.getElementsByTagName("manifest");
        if (sList == null || sList.getLength() == 0) {
            return null;
        }
        Element element = (Element) sList.item(0);
        String val = element.getAttribute("package");
        return val;
    }

    public String getAppNameVal() {
        if (document == null) {
            return null;
        }
        NodeList sList = document.getElementsByTagName("application");
        if (sList == null || sList.getLength() == 0) {
            return null;
        }
        Element element = (Element) sList.item(0);
        String val = element.getAttribute("android:name");
        return val;
    }

    public String getFirstActivityNameVal() {
        if (document == null) {
            return null;
        }
        NodeList sList = document.getElementsByTagName("intent-filter");
        if (sList == null || sList.getLength() == 0) {
            return null;
        }

        Element intentFilter = null;
        for (int i = 0; i < sList.getLength(); i++) {
            Element element = (Element) sList.item(i);
            NodeList childNodes = element.getElementsByTagName("action");
            if (childNodes == null || childNodes.getLength() == 0) {
                continue;
            }
            for (int j = 0; j < childNodes.getLength(); j++) {
                Element actionNode = (Element) childNodes.item(j);
                if ("android.intent.action.MAIN".equals(actionNode.getAttribute("android:name"))) {
                    intentFilter = element;
                    break;
                }
            }
            if (intentFilter != null) {
                break;
            }
        }
        if (intentFilter == null) {
            return null;
        }
        Element activityE = (Element) intentFilter.getParentNode();
        if (activityE != null) {
            String firstActivityNameVal = activityE.getAttribute("android:name");
            return firstActivityNameVal;
        }

        return null;
    }

}
