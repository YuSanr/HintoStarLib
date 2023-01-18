package cn.HiaXnLib.utils;


public class StringUtil {

    public static String setColor(String msg){

        return msg.replaceAll("&","§");

    }
    /**
     * 检测字符是否属于[a-z,A-Z,0-9]这三个区间内
     * @param c 检测的字符
     * @return 是否位于区间内
     */
    public static boolean isBaseChar(char c){
        if ((((int) c)>=((int) 'A') && ((int) c)<=((int) 'F')) || (((int) c)>=((int) 'a') && ((int) c)<=((int) 'f')) || (((int) c)>=((int) '0') && ((int) c)<=((int) '9'))){
            return true;
        }else {
            return false;
        }
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
                    if (!StringUtil.isBaseChar(aChar)) {
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