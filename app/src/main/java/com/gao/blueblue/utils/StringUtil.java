package com.gao.blueblue.utils;

import android.text.TextUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @program: Blueblue
 * @description: 字符串工具类
 * @author: wuzewen
 * @create: 2021-10-16 13:40
 **/
public class StringUtil {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static String PHONE = "^1[3,4,5,7,8]\\d{9}$";


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input)
                || "-1".equals(input) || "NULL".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }


    /**
     * 判断是否为手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {

        return phoneNumber.matches(PHONE);
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是纯数字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */

    public static boolean isAllNumble(String str) throws PatternSyntaxException {
        String regExp = "^[0-9]*$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 判断是否是一个http url
     */
    public static boolean isHttpUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("http") || url.startsWith("www.");
    }


     /**
      * 将手机号码的中间四位替换为“*”
      */
     public static String ensurePhoneNum(String phoneNum) {
        if (null != phoneNum) {
            try {
                if (phoneNum.contains("*")) {

                    // 字符串本身已经是带*号的，不做处理，直接返回
                    return phoneNum;
                } else {
                    return phoneNum.substring(0, 3) + "****"
                            + phoneNum.substring(7, 11);
                }
            } catch (Exception e) {
                // 防止字符串截取，越界异常
                return phoneNum;
            }
        }
        return phoneNum;
    }

     /**
      * 判断是否包含表情符号
      */
     public static Boolean isEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return true;
            }
            return false;
        }
        return false;
    }


    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    /**
     * 获取指定长度的随机字符
     *
     * @param length 指定长度
     * @return
     */
    public static final String randomChar(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**
     * 获取指定长度随机数
     *
     * @param length 指定长度
     * @return
     */
    public static final String randomNumber(int length) {
        char[] numbersAndLetters = null;
        Random randGen = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            if (randGen == null) {
                randGen = new Random();
                numbersAndLetters = ("0123456789").toCharArray();
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
        }
        return new String(randBuffer);
    }
}
