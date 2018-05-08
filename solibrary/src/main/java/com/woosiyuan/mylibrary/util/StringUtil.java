package com.woosiyuan.mylibrary.util;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.mylibrary.util.StringUtil.java
 * @author: so
 * @date: 2018-01-03 10:28
 */

public class StringUtil {
    private static char[] chineseParam = new char[]{'」', '，', '。', '？', '…', '：', '～', '【', '＃', '、', '％', '＊', '＆', '＄', '（', '‘', '’', '“', '”', '『', '〔', '｛', '【', '￥', '￡', '‖', '〖', '《', '「', '》', '〗', '】', '｝', '〕', '』', '”', '）', '！', '；', '—'};

    private StringUtil() {
    }

    public static String parseEmpty(String str) {
        if(str == null || "null".equals(str.trim())) {
            str = "";
        }

        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = Boolean.valueOf(false);
        String expr = "^[A-Za-z0-9]+$";
        if(str.matches(expr)) {
            isNoLetter = Boolean.valueOf(true);
        }

        return isNoLetter;
    }

    public static Boolean isNumber(String str) {
        Boolean isNumber = Boolean.valueOf(false);
        String expr = "^[0-9]+$";
        if(str.matches(expr)) {
            isNumber = Boolean.valueOf(true);
        }

        return isNumber;
    }

    public static Boolean isChinese(String str) {
        Boolean isChinese = Boolean.valueOf(true);
        String chinese = "[Α-￥]";
        if(!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if(!temp.matches(chinese)) {
                    isChinese = Boolean.valueOf(false);
                }
            }
        }

        return isChinese;
    }

    public static Boolean isContainChinese(String str) {
        Boolean isChinese = Boolean.valueOf(false);
        String chinese = "[Α-￥]";
        if(!isEmpty(str)) {
            for(int i = 0; i < str.length(); ++i) {
                String temp = str.substring(i, i + 1);
                if(temp.matches(chinese)) {
                    isChinese = Boolean.valueOf(true);
                }
            }
        }

        return isChinese;
    }

    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();

        for(int i = 0; i < name.length(); ++i) {
            if(!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }

        return res;
    }

    public static boolean isChinese(char c) {
        char[] var1 = chineseParam;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            char param = var1[var3];
            if(param == c) {
                return false;
            }
        }

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
}

