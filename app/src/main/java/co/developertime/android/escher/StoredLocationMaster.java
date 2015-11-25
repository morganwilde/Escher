package co.developertime.android.escher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.developertime.android.escher.database.EscherDBHelper;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class StoredLocationMaster {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static StoredLocationMaster instance;
    public static StoredLocationMaster getInstance(Context context) {
        if (instance == null) {
            instance = new StoredLocationMaster(context);
        }
        return instance;
    }
    private StoredLocationMaster(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EscherDBHelper(mContext).getWritableDatabase();
    }
}
