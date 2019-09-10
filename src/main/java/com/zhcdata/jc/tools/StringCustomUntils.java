package com.zhcdata.jc.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : win-the-goods
 * Create Date : 2019/7/1 10:42
 * JDK version : JDK1.8
 * Comments : 自定义字符串处理
 *
 * @author : 高阳
 * @version : 0.0.1
 */
public class StringCustomUntils {

  /**
   * 用户名处理
   * 用户名，判断用户名是否大于四个字符，如果等于四个字符则显示前两个字符加两个星号。
   * 如果大于四个字符则显示前两个字符和后两个字符，中间加两个星号。
   * 如果获取的两个字符不能正确显示，如第一位字母+第二位
   * @param userName
   * @return
   */
  public static String userNameHanle(String userName){
    if(StringUtils.isBlank(userName)){
      return userName;
    }
    //1.先判断是否为zc_开头
    if(userName.startsWith("zc_")){
      userName = userName.replace("zc_","");
    }
    if(isMoblieUserName(userName)){
      userName = userName.substring(0,3)+"*******"+userName.substring(userName.length()-2,userName.length());
      return userName;
    }
    try {
      if(userName.getBytes("GBK").length<=4){
          return userNameHnale4(userName);
      }else{
        if(checkcountname(userName.substring(userName.length()-1,userName.length()))){
          return userNameHnale4(userName)+"**"+userName.substring(userName.length()-1,userName.length());
        }
        return userNameHnale4(userName)+"**"+userName.substring(userName.length()-2,userName.length());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   *  判断是否为数字
   * @param mobile
   * @return
   */
  public static boolean isMoblieUserName(String mobile){
    String regular = "[0-9]{11}";
    Pattern pattern = Pattern.compile(regular);
    boolean flag = false;
    if (mobile != null) {
      Matcher matcher = pattern.matcher(mobile);
      flag = matcher.matches();
    }
    return flag;
  }
  public static boolean checkcountname(String countname) {
    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    Matcher m = p.matcher(countname);
    if (m.find()) {
      return true;
    }
      return false;
  }
  public static String userNameHnale4(String userName) {
    char[] aa = userName.toCharArray();
    String returnstr = "";
    for (int i = 0; i < aa.length; i++) {
      String str = String.valueOf(aa[i]);
      if (i == 0) {
        if (checkcountname(str)) {
            return aa[i]+"**";
        }
        returnstr  = str;
      }else{
        return returnstr+str+"**";
      }
    }
    return userName.substring(0,2)+"**";
  }

  public static void main(String args[]) throws UnsupportedEncodingException {
   /* String str = "一123高阳";
    char [] aa = str.toCharArray();
    for (char c : aa){
      System.out.println(c+"  "+ checkcountname(c+""));*/

    String str = "dd阳一f";
    System.out.println(userNameHanle(str));

  }

}
