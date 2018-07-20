package com.rectapp.sudoku.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.rectapp.sudoku.bean.Action;


public class PermissionsUtil {

    public static void request(Activity activity,String[] permissions,int requestCode,Action action){
        boolean check = true;
        for(int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(activity, permissions[i])!= PackageManager.PERMISSION_GRANTED){
                check = false;
                break;
            }
        }
        if(check){
            action.action();
        }else{
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }


}
