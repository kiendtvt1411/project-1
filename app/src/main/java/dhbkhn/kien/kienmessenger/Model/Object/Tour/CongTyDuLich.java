package dhbkhn.kien.kienmessenger.Model.Object.Tour;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 11/6/2016.
 */
public class CongTyDuLich {
    int ma_cong_ty;
    String key_cong_ty, ten_cong_ty, dia_chi, mo_ta, giam_doc, hinh_anh, sdt, web;
    float lat, lon;

    public Map<String,Object>toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("key_cong_ty", key_cong_ty);
        map.put("ten_cong_ty", ten_cong_ty);
        map.put("dia_chi", dia_chi);
        map.put("mo_ta", mo_ta);
        map.put("giam_doc", giam_doc);
        map.put("hinh_anh", hinh_anh);
        map.put("sdt", sdt);
        map.put("web", web);
        return map;
    }

    public int getMa_cong_ty() {
        return ma_cong_ty;
    }

    public void setMa_cong_ty(int ma_cong_ty) {
        this.ma_cong_ty = ma_cong_ty;
    }

    public String getKey_cong_ty() {
        return key_cong_ty;
    }

    public void setKey_cong_ty(String key_cong_ty) {
        this.key_cong_ty = key_cong_ty;
    }

    public String getTen_cong_ty() {
        return ten_cong_ty;
    }

    public void setTen_cong_ty(String ten_cong_ty) {
        this.ten_cong_ty = ten_cong_ty;
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

    public String getGiam_doc() {
        return giam_doc;
    }

    public void setGiam_doc(String giam_doc) {
        this.giam_doc = giam_doc;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public CongTyDuLich() {
    }
}
