package cn.ucai.fulicenters.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bk124 on 2016/10/25.
 */
public class SharePreferenceUtils {
    private static final String SHARE_NAME="saveUserInfo";
    private static SharePreferenceUtils instence;
    private SharedPreferences mSharePreferences;
    private SharedPreferences.Editor mEditor;
    private static final String SHARE_KEY_USER_NAME="share_key_user_name";

    public SharePreferenceUtils(Context context){
        mSharePreferences=context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharePreferences.edit();
    }

    public static SharePreferenceUtils getInstence(Context context){
        if (instence==null){
            instence=new SharePreferenceUtils(context);
        }
        return instence;
    }
    public void saveUser(String username){
        mEditor.putString(SHARE_KEY_USER_NAME,username);
        mEditor.commit();
    }
    public String getUsser(){
        return mSharePreferences.getString(SHARE_KEY_USER_NAME,null);
    }

    public void removeUser(){
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
