package com.technohest.Tools;

import com.technohest.core.model.Action;

import java.util.Vector;

/**
 * Sort actions by time.
 * Created by vilddjur on 2015-05-12.
 */
public class Sort {
    public static Vector<Action> sortTime(Vector<Action> vector){
        boolean done = false;
        Action temp;
        while (!done){
            done = true;
            for(int i = 0;i<vector.size()-1;i++){
                if(vector.get(i).getTimestamp() > vector.get(i+1).getTimestamp()){
                    temp = vector.get(i);
                    vector.set(i, vector.get(i+1));
                    vector.set(i+1, temp);
                    done = false;
                }
            }
        }

        return vector;
    }
}
