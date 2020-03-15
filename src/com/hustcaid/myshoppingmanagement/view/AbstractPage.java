package com.hustcaid.myshoppingmanagement.view;

import java.util.List;


/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 * @Author: caid Wang
 ******************************************************************************/
abstract class AbstractPage {
    /**
     * 打印页面头
     */
    public abstract void printHead();

    /**
     * 打印单条目录
     */
    public abstract void printEntry();

    public static void printList(List< ? extends AbstractPage> list) {
        if (list == null || list.size() == 0) {
            return ;
        }
        list.get(0).printHead();
        for (AbstractPage e: list) {
            e.printEntry();
        }
    }

}
