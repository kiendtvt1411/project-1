package dhbkhn.kien.kienmessenger.Model.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 10/21/2016.
 */
public class Comment {
    String key_comment, avatar_url , username, location, time, content, image_url;
    int like;
    boolean clickLike;

    public Map<String,Object> toMap(){
        Map<String, Object> hm = new HashMap<>();
        hm.put("key_comment", key_comment);
        hm.put("avatar_url", avatar_url);
        hm.put("username", username);
        hm.put("location", location);
        hm.put("image_url", image_url);
        hm.put("time", time);
        hm.put("content", content);
        hm.put("like", like);
        hm.put("clickLike", clickLike);
        return hm;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getKey_comment() {
        return key_comment;
    }

    public void setKey_comment(String key_comment) {
        this.key_comment = key_comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isClickLike() {
        return clickLike;
    }

    public void setClickLike(boolean clickLike) {
        this.clickLike = clickLike;
    }

    public Comment() {
        clickLike = false;
    }
}
