package dhbkhn.kien.kienmessenger.Model.Object.Tour;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 11/10/2016.
 */
public class ChiDeShow {
    String ten_dd, hinh_dd, mota_dd, key_dd;
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("ten_dd", ten_dd);
        map.put("hinh_dd", hinh_dd);
        map.put("mota_dd", mota_dd);
        map.put("key_dd", key_dd);
        return map;
    }

    public String getKey_dd() {
        return key_dd;
    }

    public void setKey_dd(String key_dd) {
        this.key_dd = key_dd;
    }

    public String getMota_dd() {
        return mota_dd;
    }

    public void setMota_dd(String mota_dd) {
        this.mota_dd = mota_dd;
    }

    public String getTen_dd() {
        return ten_dd;
    }

    public void setTen_dd(String ten_dd) {
        this.ten_dd = ten_dd;
    }

    public String getHinh_dd() {
        return hinh_dd;
    }

    public void setHinh_dd(String hinh_dd) {
        this.hinh_dd = hinh_dd;
    }

    public ChiDeShow() {
    }
}
