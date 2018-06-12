package edu.ksu.cs.benign;

import android.support.v4.app.Fragment;

/**
 * Created by Joy on 5/15/18.
 */

public class Util {
    public static Fragment getFragmentInstance(String fname){
        if(fname.contains(".EmailFragment")){
            try{
                return (EmailFragment) Class.forName(fname).newInstance();
            }
            catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
                throw new RuntimeException(e);
            }
        }
        if(fname.contains(".BlankFragment")){
            try{
                return (BlankFragment) Class.forName(fname).newInstance();
            }
            catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
