package cn.ucai.fulicenters.dao;

import android.content.Context;

import cn.ucai.fulicenters.bean.User;

/**
 * Created by bk124 on 2016/10/24.
 */
public class DBManager {
    private static DBManager dnMgr=new DBManager();
    private static DBOpenHelper mHelper;

    public DBManager() {

    }

    public static DBManager onInit(Context context){
        if (mHelper==null){
            mHelper=DBOpenHelper.onInit(context);
        }
        return dnMgr;
    }

    public static void closeDB(){
        if (mHelper!=null){
            mHelper.closeDB();
        }
    }

    public boolean saveUser(User user){
        return false;
    }
    public User getUser(String username){
        return null;
    }
    public boolean updateUser(User user){
        return false;
    }
}
