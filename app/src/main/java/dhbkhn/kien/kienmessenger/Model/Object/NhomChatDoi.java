package dhbkhn.kien.kienmessenger.Model.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 9/24/2016.
 */
public class NhomChatDoi {
    String user1,user2;

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public NhomChatDoi() {
    }

    public Map<String,Object> toMap(){
        Map<String,Object>hm = new HashMap<>();
        hm.put("user1",user1);
        hm.put("user2",user2);
        return hm;
    }
}
