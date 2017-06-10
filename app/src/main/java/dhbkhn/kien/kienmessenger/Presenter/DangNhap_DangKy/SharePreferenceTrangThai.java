package dhbkhn.kien.kienmessenger.Presenter.DangNhap_DangKy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiend on 9/26/2016.
 */
public class SharePreferenceTrangThai {
    public List<String> layDuLieuTrongSharePreferences(Context context) {
        List<String>dsDL = new ArrayList<>();
        SharedPreferences cacheDangNhap = context.getSharedPreferences("ChatDoi", Context.MODE_PRIVATE);
        dsDL.add(cacheDangNhap.getString("ban", null));
        dsDL.add(cacheDangNhap.getString("emailban", null));
        dsDL.add(cacheDangNhap.getString("nguoidung", null));
        dsDL.add(cacheDangNhap.getString("noidung", null));
        dsDL.add(cacheDangNhap.getString("thoigian", null));
        dsDL.add(cacheDangNhap.getString("trangthai", null));
        return dsDL;
    }

    public void capNhatCacheDangNhap(Context context, String friendName, String emailFriend, String author, String content, String date, String status) {
        SharedPreferences cacheDangNhap = context.getSharedPreferences("ChatDoi", Context.MODE_PRIVATE);
        //mode private de cac ung dung khac k truy cap dc vao sharedpreferences nay
        SharedPreferences.Editor editor = cacheDangNhap.edit();
        editor.putString("ban", friendName);
        editor.putString("emailban", emailFriend);
        editor.putString("nguoidung", author);
        editor.putString("noidung", content);
        editor.putString("thoigian", date);
        editor.putString("trangthai", status);
        editor.commit();
    }

    public int laySoHieuNhom(Context context){
        SharedPreferences cacheDanhBa = context.getSharedPreferences("DungApp", Context.MODE_PRIVATE);
        return cacheDanhBa.getInt("sohieu",0);
    }

    public void capNhatSoHieuNhom(Context context, int soLuong){
        SharedPreferences cacheDanhBa = context.getSharedPreferences("DungApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cacheDanhBa.edit();
        editor.putInt("sohieu",soLuong);
        editor.commit();
        Log.d("he",soLuong+"");
    }

    public List<String> layUserPass(Context context){
        List<String> ds = new ArrayList<>();
        SharedPreferences cacheUser = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        String email = cacheUser.getString("email", "");
        String name = cacheUser.getString("name", "");
        String pass = cacheUser.getString("pass", "");
        ds.add(email);
        ds.add(name);
        ds.add(pass);
        return ds;
    }

    public void capNhatUserPass(Context context, String email, String name, String pass){
        SharedPreferences cacheUser = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cacheUser.edit();
        editor.putString("email",email);
        editor.putString("name",name);
        editor.putString("pass",pass);
        editor.commit();
    }
}
