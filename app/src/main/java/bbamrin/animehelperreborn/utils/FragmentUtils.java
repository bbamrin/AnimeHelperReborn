package bbamrin.animehelperreborn.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import bbamrin.animehelperreborn.Model.StaticVars;

public class FragmentUtils {
    public static int attachFragmentToActivity(FragmentManager manager, Fragment fragment,int fragmentContainer){
        if(manager!=null && fragment!=null){
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(fragmentContainer, fragment);
            transaction.addToBackStack(StaticVars.FRAGMENT_BACKSTACK);
            transaction.commit();
            return StaticVars.SUCCES_CODE;
        } else{
            return StaticVars.FAIL_CODE;
        }
    }

    public static int replaceSupportFragment(android.support.v4.app.FragmentManager manager, android.support.v4.app.Fragment fragment, int fragmentContainer){
        if(manager!=null && fragment!=null){
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(fragmentContainer, fragment);
            transaction.addToBackStack(StaticVars.FRAGMENT_BACKSTACK);
            transaction.commit();
            return StaticVars.SUCCES_CODE;
        } else{
            return StaticVars.FAIL_CODE;
        }
    }

    public static int attachSupportFragmentToActivity(android.support.v4.app.FragmentManager manager, android.support.v4.app.Fragment fragment,int fragmentContainer){

        if(manager!=null && fragment!=null){
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(fragmentContainer, fragment);
            transaction.commit();
            return StaticVars.SUCCES_CODE;
        } else{
            return StaticVars.FAIL_CODE;
        }

    }







}
