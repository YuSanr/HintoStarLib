package cn.HiaXnLib.utils;

import java.util.Scanner;

public class charJudge {

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

}
