package dhbkhn.kien.kienmessenger.Model.Object;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 9/24/2016.
 */
@IgnoreExtraProperties
public class Friend implements Serializable{
    String email_friend,username_friend, avatar_url, nick_name;

    public String getEmail_friend() {
        return email_friend;
    }

    public void setEmail_friend(String email_friend) {
        this.email_friend = email_friend;
    }

    public String getUsername_friend() {
        return username_friend;
    }

    public void setUsername_friend(String username_friend) {
        this.username_friend = username_friend;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Friend() {
    }

    public Friend(String email_friend, String username_friend) {
        this.email_friend = email_friend;
        this.username_friend = username_friend;
    }

    @Exclude
    public Map<String,Object> toMap(){
        Map<String,Object>hm = new HashMap<>();
        hm.put("email_friend",email_friend);
        hm.put("username_friend",username_friend);
        hm.put("avatar_url",avatar_url);
        hm.put("nick_name",nick_name);
        return hm;
    }
}
