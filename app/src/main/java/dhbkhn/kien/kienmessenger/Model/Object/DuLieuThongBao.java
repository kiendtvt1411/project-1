package dhbkhn.kien.kienmessenger.Model.Object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 10/5/2016.
 */
public class DuLieuThongBao implements Serializable {
    int tongTB, tbChuaXem;
    String friend;

    public int getTongTB() {
        return tongTB;
    }

    public void setTongTB(int tongTB) {
        this.tongTB = tongTB;
    }

    public int getTbChuaXem() {
        return tbChuaXem;
    }

    public void setTbChuaXem(int tbChuaXem) {
        this.tbChuaXem = tbChuaXem;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public DuLieuThongBao() {
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("user", friend);
        map.put("tongTB", tongTB);
        map.put("tbChuaXem", tbChuaXem);
        return map;
    }
}
