package cn.ucai.fulicenter_2017.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.ucai.fulicenter_2017.data.bean.User;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class DBManager {
    private static DBOpenHelper sHelper;
    private static DBManager mDBManager=new DBManager();
    public static DBManager getInstance(){
        return  mDBManager;
    }
    public void initDB(Context context){
        sHelper=DBOpenHelper.getInstance(context);
    }
    public synchronized boolean saveUser(User user){
        Log.i("main", "DBManager.user:" + user);
        SQLiteDatabase database=sHelper.getWritableDatabase();
        if(database.isOpen()){
            ContentValues values=new ContentValues();
            values.put(DBOpenHelper.USER_COLUMN_NAME,user.getMuserName());
            values.put(DBOpenHelper.USER_COLUMN_NICK,user.getMuserNick());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR,user.getMavatarId());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_PATH,user.getMavatarPath());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_TYPE,user.getMavatarType());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_SUFFIX,user.getMavatarSuffix());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_UPDATE_TIME,user.getMavatarLastUpdateTime());
            long insert=database.replace(DBOpenHelper.USER_TABLE_NAME,null,values);
            return insert>0?true:false;
        }
        return false;

    }
    /**
     * muserName : a952700
     * muserNick : 士大夫
     * mavatarId : 72
     * mavatarPath : user_avatar
     * mavatarSuffix : .jpg
     * mavatarType : 0
     * mavatarLastUpdateTime : 1476262984280
     */
    public synchronized User getUser(String userName){
        User user=null;
        SQLiteDatabase database=sHelper.getReadableDatabase();
        if(database.isOpen()){
            Cursor cursor = database.rawQuery("select * from " + DBOpenHelper.USER_TABLE_NAME + " where " +
                    DBOpenHelper.USER_COLUMN_NAME + "=?", new String[]{userName});
            if(cursor.moveToNext()){
                user=new User();
                String usernick=cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_NICK));
                user.setMuserNick(usernick);
                user.setMuserName(userName);
                String avatarpath=cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_PATH));
                user.setMavatarPath(avatarpath);
                int avatartype=cursor.getInt(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_TYPE));
                user.setMavatarType(avatartype);
                String suffix=cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_SUFFIX));
                user.setMavatarSuffix(suffix);
                String update_time=cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_UPDATE_TIME));
                user.setMavatarLastUpdateTime(update_time);
                Log.i("main", "DBManager.getUser().user:" + user);
            }

        }
        return user;
    }

}
