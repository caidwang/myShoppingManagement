package view;

import java.util.List;

/******************************************************************************
 *  Compilation:  
 *  Execution:    
 *  Dependencies: 
 *  Description:    
 *
 ******************************************************************************/
public abstract class AbstractEntry {

    public abstract void printHead();

    public abstract void printEntry();

    public static void printList(List< ? extends AbstractEntry> list) {
        if (list == null || list.size() == 0) return ;
        list.get(0).printHead();
        for (AbstractEntry e: list) {
            e.printEntry();
        }
    }

}
