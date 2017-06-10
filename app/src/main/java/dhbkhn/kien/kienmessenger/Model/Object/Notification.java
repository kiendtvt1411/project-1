package dhbkhn.kien.kienmessenger.Model.Object;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 10/5/2016.
 */
public class Notification {
    String content, author, email;
    boolean consequence;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isConsequence() {
        return consequence;
    }

    public void setConsequence(boolean consequence) {
        this.consequence = consequence;
    }

    public Notification() {

    }

    public Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("author", author);
        map.put("content", content);
        map.put("email",email);
        map.put("consequence", consequence);
        return map;
    }
}
