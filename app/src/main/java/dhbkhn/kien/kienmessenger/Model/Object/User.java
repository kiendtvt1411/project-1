package dhbkhn.kien.kienmessenger.Model.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 9/20/2016.
 */
public class User {
    String username, email, password, phone, avatar_url;
    boolean online;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {
        this.online = false;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> results = new HashMap<>();
        results.put("username",username);
        results.put("email",email);
        results.put("password",password);
        results.put("phone",phone);
        results.put("online",false);
        return results;
    }
}
