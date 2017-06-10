package dhbkhn.kien.kienmessenger.Model.Object;

import java.io.Serializable;

/**
 * Created by kiend on 9/22/2016.
 */
public class ShowLassMess implements Serializable {
    ChatMessage message;
    Friend friend;
    String key_nhom;
    int so_lan_thong_bao;

    public int getSo_lan_thong_bao() {
        return so_lan_thong_bao;
    }

    public void setSo_lan_thong_bao(int so_lan_thong_bao) {
        this.so_lan_thong_bao = so_lan_thong_bao;
    }

    public String getKey_nhom() {
        return key_nhom;
    }

    public void setKey_nhom(String key_nhom) {
        this.key_nhom = key_nhom;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public ShowLassMess() {
        so_lan_thong_bao = 0;
    }
}
