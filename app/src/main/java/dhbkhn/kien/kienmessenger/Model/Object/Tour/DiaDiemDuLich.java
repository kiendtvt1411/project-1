package dhbkhn.kien.kienmessenger.Model.Object.Tour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiend on 11/9/2016.
 */
public class DiaDiemDuLich {
    int ma_dia_diem;
    String key_dia_diem, ten_dia_diem, dia_chi, mo_ta, chu_dia_diem;
    Float lat, lon;
    List<String> list_hinh_anh_url;

    public Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("key_dia_diem", key_dia_diem);
        map.put("ten_dia_diem", ten_dia_diem);
        map.put("dia_chi", dia_chi);
        map.put("mo_ta", mo_ta);
        map.put("chu_dia_diem", chu_dia_diem);
        map.put("lat", lat);
        map.put("lon", lon);
        map.put("list_hinh_anh_url", list_hinh_anh_url);
        return map;
    }

    public int getMa_dia_diem() {
        return ma_dia_diem;
    }

    public void setMa_dia_diem(int ma_dia_diem) {
        this.ma_dia_diem = ma_dia_diem;
    }

    public String getKey_dia_diem() {
        return key_dia_diem;
    }

    public void setKey_dia_diem(String key_dia_diem) {
        this.key_dia_diem = key_dia_diem;
    }

    public String getTen_dia_diem() {
        return ten_dia_diem;
    }

    public void setTen_dia_diem(String ten_dia_diem) {
        this.ten_dia_diem = ten_dia_diem;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getChu_dia_diem() {
        return chu_dia_diem;
    }

    public void setChu_dia_diem(String chu_dia_diem) {
        this.chu_dia_diem = chu_dia_diem;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public List<String> getList_hinh_anh_url() {
        return list_hinh_anh_url;
    }

    public void setList_hinh_anh_url(List<String> list_hinh_anh_url) {
        this.list_hinh_anh_url = list_hinh_anh_url;
    }

    public DiaDiemDuLich() {
    }
}
