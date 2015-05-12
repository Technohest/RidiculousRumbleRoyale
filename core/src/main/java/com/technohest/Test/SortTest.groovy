package com.technohest.Test

import com.technohest.Tools.Sort
import com.technohest.core.network.Action
import org.junit.Test

/**
 * Created by vilddjur on 2015-05-12.
 */
class SortTest extends GroovyTestCase {
    @Test
    public void testTimeSort(){
        Vector<Action> actionVector;
        actionVector = new Vector<Action>();
        for(int i=0;i<10;i++){
            Action temp = new Action(Action.ActionID.Jump, i);
            actionVector.add(temp);
        }
        actionVector = Sort.sortTime(actionVector);
        for(int i=0;i<actionVector.size();i++){
            System.out.println(actionVector.get(i).timestamp);
        }
    }
}
