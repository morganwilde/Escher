package co.developertime.android.escher.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.developertime.android.escher.database.EscherDBSchema.LocationTable;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class EscherDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "escherDatabase.db";

    public EscherDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + LocationTable.NAME + " ( " +
                        "_id INTEGER PRIMARY KEY autoincrement, " +
                        LocationTable.Columns.ACCURACY + ", " +
                        LocationTable.Columns.ALTITUDE + ", " +
                        LocationTable.Columns.LATITUDE + ", " +
                        LocationTable.Columns.LONGITUDE + ", " +
                        LocationTable.Columns.TIME +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
