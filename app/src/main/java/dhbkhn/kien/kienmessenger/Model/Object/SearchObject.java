package dhbkhn.kien.kienmessenger.Model.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 12/8/2016.
 */
public class SearchObject {
    private String display_name, avatar_url, type, key;

    private Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("display_name", display_name);
        map.put("avatar_url", avatar_url);
        map.put("key", key);
        map.put("type", type);
        return map;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchObject() {
    }
}
