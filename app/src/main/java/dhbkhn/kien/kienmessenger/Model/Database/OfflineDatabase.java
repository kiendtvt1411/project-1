package dhbkhn.kien.kienmessenger.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiend on 10/5/2016.
 */
public class OfflineDatabase extends SQLiteOpenHelper {
    public static final String DBNAME_NOTIFY = "SQL_NOTIFICATION";
    public static final int DBVER_NOTIFY = 1;

    public static final String TB_NOTIFICATION = "THONGBAO";
    public static final String TB_NOTIFICATION_MATB = "MATB";
    public static final String TB_NOTIFICATION_TENND = "TENND";
    public static final String TB_NOTIFICATION_TONGTB = "TONGTB";
    public static final String TB_NOTIFICATION_TBCHUAXEM = "TBCHUAXEM";

    public static final String TB_GROUPCHAT = "CHATNHOM";
    public static final String TB_GROUPCHAT_MAND = "MAND";
    public static final String TB_GROUPCHAT_TENND = "TENND";
    public static final String TB_GROUPCHAT_SONHOM = "SONHOM";

    public OfflineDatabase(Context context) {
        super(context, DBNAME_NOTIFY, null, DBVER_NOTIFY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbThongBao = "CREATE TABLE " + TB_NOTIFICATION + " ("
                + TB_NOTIFICATION_MATB + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NOTIFICATION_TENND + " TEXT, "
                + TB_NOTIFICATION_TONGTB + " INT, "
                + TB_NOTIFICATION_TBCHUAXEM + " INT);";

        db.execSQL(tbThongBao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
