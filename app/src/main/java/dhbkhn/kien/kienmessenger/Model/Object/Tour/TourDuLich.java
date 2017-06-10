package dhbkhn.kien.kienmessenger.Model.Object.Tour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiend on 11/9/2016.
 */
public class TourDuLich implements Serializable{
    int ma_tour, gia_tien, ma_cong_ty;
    String key_tour, ten_tour, mo_ta, anh_bia_url, lien_he, sdt, phuong_tien, xuat_phat, thoi_gian;
    List<String> list_lo_trinh;

    public Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("key_tour", key_tour);
        map.put("ten_tour", ten_tour);
        map.put("mo_ta", mo_ta);
        map.put("anh_bia_url", anh_bia_url);
        map.put("lien_he", lien_he);
        map.put("sdt", sdt);
        map.put("phuong_tien", phuong_tien);
        map.put("gia_tien", gia_tien);
        map.put("thoi_gian", thoi_gian);
        map.put("xuat_phat", xuat_phat);
        map.put("list_lo_trinh", list_lo_trinh);
        return map;
    }

    public List<String> getList_lo_trinh() {
        return list_lo_trinh;
    }

    public void setList_lo_trinh(List<String> list_lo_trinh) {
        this.list_lo_trinh = list_lo_trinh;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public int getMa_tour() {
        return ma_tour;
    }

    public void setMa_tour(int ma_tour) {
        this.ma_tour = ma_tour;
    }

    public int getGia_tien() {
        return gia_tien;
    }

    public void setGia_tien(int gia_tien) {
        this.gia_tien = gia_tien;
    }



    public int getMa_cong_ty() {
        return ma_cong_ty;
    }

    public void setMa_cong_ty(int ma_cong_ty) {
        this.ma_cong_ty = ma_cong_ty;
    }

    public String getKey_tour() {
        return key_tour;
    }

    public void setKey_tour(String key_tour) {
        this.key_tour = key_tour;
    }

    public String getTen_tour() {
        return ten_tour;
    }

    public void setTen_tour(String ten_tour) {
        this.ten_tour = ten_tour;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getAnh_bia_url() {
        return anh_bia_url;
    }

    public void setAnh_bia_url(String anh_bia_url) {
        this.anh_bia_url = anh_bia_url;
    }

    public String getLien_he() {
        return lien_he;
    }

    public void setLien_he(String lien_he) {
        this.lien_he = lien_he;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPhuong_tien() {
        return phuong_tien;
    }

    public void setPhuong_tien(String phuong_tien) {
        this.phuong_tien = phuong_tien;
    }

    public String getXuat_phat() {
        return xuat_phat;
    }

    public void setXuat_phat(String xuat_phat) {
        this.xuat_phat = xuat_phat;
    }

    public TourDuLich() {
    }
}
