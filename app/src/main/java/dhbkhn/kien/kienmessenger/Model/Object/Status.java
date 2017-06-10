package dhbkhn.kien.kienmessenger.Model.Object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiend on 10/19/2016.
 */
public class Status implements Serializable {
    private String content, username, location, date, avatar_url, type, key_status;
    private List<String> list_image_url;
    private int likes, comments, shares;
    private boolean clickLike, duocShare;

    public String getContent() {
        return content;
    }

    public Map<String,Object> toMap(){
        Map<String, Object> hm = new HashMap<>();
        hm.put("username", username);
        hm.put("avatar_url", avatar_url);
        hm.put("content", content);
        hm.put("date", date);
        hm.put("location", location);
        hm.put("type", type);
        hm.put("list_image_url", list_image_url);
        hm.put("likes", likes);
        hm.put("comments", comments);
        hm.put("shares", shares);
        hm.put("key_status", key_status);
        hm.put("clickLike", clickLike);
        hm.put("duocShare", duocShare);
        return hm;
    }

    public boolean isDuocShare() {
        return duocShare;
    }

    public void setDuocShare(boolean duocShare) {
        this.duocShare = duocShare;
    }

    public boolean isClickLike() {
        return clickLike;
    }

    public void setClickLike(boolean clickLike) {
        this.clickLike = clickLike;
    }

    public String getKey_status() {
        return key_status;
    }

    public void setKey_status(String key_status) {
        this.key_status = key_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public List<String> getList_image_url() {
        return list_image_url;
    }

    public void setList_image_url(List<String> list_image_url) {
        this.list_image_url = list_image_url;
    }

    public Status() {
        clickLike = false;
        duocShare = false;
    }


}
