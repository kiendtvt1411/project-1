package dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 11/3/2016.
 */
public class PageDoanhNghiep implements Serializable{
    String ten_cty, diachi_cty, key_page_cty, avatar_url_cty, anh_bia_url_cty, mo_ta_cty;
    String giam_doc_cty;

    public Map<String,Object> toMap(){
        Map<String, Object> hm = new HashMap<>();
        hm.put("ten_cty", ten_cty);
        hm.put("diachi_cty", diachi_cty);
        hm.put("key_page_cty", key_page_cty);
        hm.put("mo_ta_cty", mo_ta_cty);
        hm.put("giam_doc_cty", giam_doc_cty);
        return hm;
    }

    public String getGiam_doc_cty() {
        return giam_doc_cty;
    }

    public void setGiam_doc_cty(String giam_doc_cty) {
        this.giam_doc_cty = giam_doc_cty;
    }

    public String getTen_cty() {
        return ten_cty;
    }

    public void setTen_cty(String ten_cty) {
        this.ten_cty = ten_cty;
    }

    public String getDiachi_cty() {
        return diachi_cty;
    }

    public void setDiachi_cty(String diachi_cty) {
        this.diachi_cty = diachi_cty;
    }

    public String getKey_page_cty() {
        return key_page_cty;
    }

    public void setKey_page_cty(String key_page_cty) {
        this.key_page_cty = key_page_cty;
    }

    public String getAvatar_url_cty() {
        return avatar_url_cty;
    }

    public void setAvatar_url_cty(String avatar_url_cty) {
        this.avatar_url_cty = avatar_url_cty;
    }

    public String getAnh_bia_url_cty() {
        return anh_bia_url_cty;
    }

    public void setAnh_bia_url_cty(String anh_bia_url_cty) {
        this.anh_bia_url_cty = anh_bia_url_cty;
    }

    public String getMo_ta_cty() {
        return mo_ta_cty;
    }

    public void setMo_ta_cty(String mo_ta_cty) {
        this.mo_ta_cty = mo_ta_cty;
    }

    public PageDoanhNghiep() {
    }
}
