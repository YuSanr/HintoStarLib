package cn.HiaXnLib.utils;



import java.util.Scanner;

public class setColor {

    public static String setColor(String msg){

        return msg.replaceAll("&","§");

    }

    public static String setBaseColor(String str) {
        String base;
        String replaceBase = "";
        StringBuffer sb;
        StringBuffer sb1;
        String str1 = str;
        while (str.contains("#")) {
            int index = str.indexOf('#');
            sb = new StringBuffer();
            sb1 = new StringBuffer();
            try {
                base = str.substring(index + 1, index + 7);
                replaceBase = str.substring(index, index + 7);
                char[] chars = base.toCharArray();
                boolean allChar = true;
                // 检测是否都为 大小写字母 和 数字
                for (char aChar : chars) {
                    if (!charJudge.isBaseChar(aChar)) {
                        allChar = false;
                        break;
                    }
                }
                if (allChar) {
                    sb.append("§X");
                    for (char aChar : chars) {
                        sb.append("§" + aChar);
                        sb1.append("§" + aChar);
                    }
                }else {
                    sb.append("#");
                    for (char aChar : chars) {
                        sb.append(aChar);
                        sb1.append(aChar);
                    }
                }
                str1 = str1.replace(replaceBase, sb.toString());
                str = str.replace(replaceBase,sb1.toString());
            } catch (Exception e) {
            }
        }
        return str1;
    }
}