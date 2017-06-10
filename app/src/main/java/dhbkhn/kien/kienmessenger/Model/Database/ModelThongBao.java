package dhbkhn.kien.kienmessenger.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by kiend on 10/5/2016.
 */
public class ModelThongBao {
    SQLiteDatabase mSqlDatabase;

    public void moKetNoiSQL(Context context) {
        OfflineDatabase offlineDatabase = new OfflineDatabase(context);
        mSqlDatabase = offlineDatabase.getWritableDatabase();
    }

    public boolean themThongBaoTheoNguoiDung(String nguoiDung) {
        ContentValues values = new ContentValues();
        values.put(OfflineDatabase.TB_NOTIFICATION_TENND, nguoiDung);
        values.put(OfflineDatabase.TB_NOTIFICATION_TONGTB, 0);
        values.put(OfflineDatabase.TB_NOTIFICATION_TBCHUAXEM, 0);
        long id = mSqlDatabase.insert(OfflineDatabase.TB_NOTIFICATION, null, values);
        if (id != 0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean capNhatSoLuongThongBaoNguoiDung(String nguoiDung, int tongTB, int tbChuaXem) {
        ContentValues values = new ContentValues();
        values.put(OfflineDatabase.TB_NOTIFICATION_TONGTB, tongTB);
        values.put(OfflineDatabase.TB_NOTIFICATION_TBCHUAXEM, tbChuaXem);
        int ktra = mSqlDatabase.update(OfflineDatabase.TB_NOTIFICATION, values,
                OfflineDatabase.TB_NOTIFICATION_TENND + " = '" + nguoiDung+"'", null);
        if (ktra > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int soLuongThongBaoChuaXem(String nguoiDung) {
        int tbChuaXem = 0;
        String sql = "SELECT * FROM " + OfflineDatabase.TB_NOTIFICATION + " WHERE TENND = '" + nguoiDung + "'";
        Cursor cursor = mSqlDatabase.rawQuery(sql, null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            tbChuaXem = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.TB_NOTIFICATION_TBCHUAXEM));
            Log.d("tbchuaxem", tbChuaXem + "");
        }
        return tbChuaXem;
    }

    public int soLuongThongBao(String nguoiDung) {
        int tongTB = 0;
        String sql = "SELECT * FROM " + OfflineDatabase.TB_NOTIFICATION + " WHERE TENND = '" + nguoiDung + "'";
        Cursor cursor = mSqlDatabase.rawQuery(sql, null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            tongTB = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.TB_NOTIFICATION_TONGTB));
            Log.d("tbchuaxem", tongTB + "");
        }
        return tongTB;
    }

    public boolean ktraTonTaiNguoiDung(String nguoiDung) {
        String sql = "SELECT * FROM " + OfflineDatabase.TB_NOTIFICATION + " WHERE TENND = '" + nguoiDung + "'";
        Cursor cursor = mSqlDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}
