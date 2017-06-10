package dhbkhn.kien.kienmessenger.Model.Object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiend on 10/1/2016.
 */
public class ProfileGroupChat implements Serializable {
    String ten_nhom;
    List<Friend> list_friend;
    String key_nhom;

    public String getKey_nhom() {
        return key_nhom;
    }

    public void setKey_nhom(String key_nhom) {
        this.key_nhom = key_nhom;
    }

    public String getTen_nhom() {
        return ten_nhom;
    }

    public void setTen_nhom(String ten_nhom) {
        this.ten_nhom = ten_nhom;
    }

    public List<Friend> getList_friend() {
        return list_friend;
    }

    public void setList_friend(List<Friend> list_friend) {
        this.list_friend = list_friend;
    }

    public ProfileGroupChat() {
    }

    public Map<String,Object> toMap(){
        Map<String, Object> hm = new HashMap<>();
        hm.put("ten_nhom",ten_nhom);
        hm.put("list_friend",list_friend);
        hm.put("key_nhom", key_nhom);
        return hm;
    }

    @Override
    public String toString() {
        return "ProfileGroupChat{" +
                "ten_nhom='" + ten_nhom + '\'' +
                ", list_friend=" + list_friend.get(0).getUsername_friend() + list_friend.get(1).getUsername_friend() +
                ", key_nhom=" + key_nhom +
                '}';
    }
}
