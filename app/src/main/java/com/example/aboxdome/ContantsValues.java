package com.example.aboxdome;

import java.util.HashMap;
import java.util.Map;

public class ContantsValues {
    public static Map<String,String> codeValues = new HashMap<String,String>();

    public static String HH_USERNAME = "username";
    public static String HH_PASSWORD = "password";
    public static String HH_AUTOLOGIN = "autologin";//自动登录
    public static String HH_REMEMBER = "remember";//记住密码
    public static String HH_FILENAME = "userInfo";
    public static String DE_RED = "a8-红外控制器";
    public static int HH_NUMBER = 30;
    public static String []options = {"重新学习","修改名称","删除按钮"};
    public static int HH_INT = 0;

    public static void init(){
        codeValues.put("00000","登录成功");
        codeValues.put("00001","数据库操作失败");
        codeValues.put("10001","TOKEN不存在或长度不足");
        codeValues.put("10002","TOKEN已失效");
        codeValues.put("10003","TOKEN中的SN号不正");
        codeValues.put("10004","签名不存在");
        codeValues.put("10005","时间戳不存在");
        codeValues.put("10006","签名不正确");
        codeValues.put("20000","请求超时");
        codeValues.put("20001","用户名或密码不存在");
        codeValues.put("20002","用户不存在或已暂停使用");
        codeValues.put("20003","登录失败次数过多");
        codeValues.put("20004","用户名或密码不正确");
        codeValues.put("20005","本地用户权限不足");
        codeValues.put("20101","服务器端URL或酒店名或房间号或APIKEY不存在");
        codeValues.put("20201","红外KEY不存在");
        codeValues.put("20202","红外码值不存在");
        codeValues.put("20203","红外设备不存在");
        codeValues.put("20204","红外码库导入失败");
        codeValues.put("20205","红外发射失败");
        codeValues.put("20206","红外设备已离线");
        codeValues.put("20501","参数不正_插座状态不存在");
        codeValues.put("20502","插座设备不存在");
        codeValues.put("20503","插座设备控制失败");
        codeValues.put("20504","插座设备已离线");
        codeValues.put("20601","参数不正_开关状态不存在");
        codeValues.put("20602","参数不正_开关类型不存在(标识几开开关)");
        codeValues.put("20603","开关设备不存在");
        codeValues.put("20604","开关设备控制失败");
        codeValues.put("20605","开关设备已掉线");
        codeValues.put("20801","参数不正_设备名称不存在");
        codeValues.put("20802","端末设备不存在");
        codeValues.put("20803","端末状态属性不存在");
        codeValues.put("20804","参数不正_报警时长不正确");
        codeValues.put("20805","端末设备已离线");
        codeValues.put("20806","设备控制失败");
    }

}
