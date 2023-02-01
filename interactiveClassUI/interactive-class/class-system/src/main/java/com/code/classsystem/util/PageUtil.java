package com.code.classsystem.util;

import java.util.List;

/**
 * @author eleven
 * @date 2022/5/7 10:33
 * @description 分页工具类
 */
public class PageUtil {
    public static List splitList(List list,Integer currentPage,Integer pageSize){
        if(currentPage == null){
            currentPage = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = fromIndex + pageSize;
        toIndex = Math.min(toIndex,list.size());
        return list.subList(fromIndex,toIndex);
    }
}
