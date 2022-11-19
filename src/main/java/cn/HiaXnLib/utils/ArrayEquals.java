package cn.HiaXnLib.utils;

import java.util.List;
import java.util.Map;

public class  ArrayEquals {

    /**
     * 检测集合的元素是否完全相同
     * @param list 集合1
     * @param list1 集合2
     * @return 相同返回true
     */
    public static <M> boolean listEquals(List<M> list,List<M> list1){
        if (list.isEmpty() && list1.isEmpty()){
            return true;
        }
        if (list.size() == list1.size()){

            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).equals(list1.get(i))){
                    return false;
                }

            }

        }
        return true;
    }

    public static boolean mapEquals(Map map1 , Map map2){

        if (map1.isEmpty() && map2.isEmpty()){
            return true;
        }
        if (map1.size() == map2.size()){


            for (Object o : map1.entrySet()) {

                try{
                    if (!map2.get(o).equals(map1.get(o))){
                        return false;
                    }

                }catch (NullPointerException e){
                    return false;
                }
            }

        }
        return true;

    }


}
