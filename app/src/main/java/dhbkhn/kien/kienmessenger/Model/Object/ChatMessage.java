package dhbkhn.kien.kienmessenger.Model.Object;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiend on 9/20/2016.
 */
@IgnoreExtraProperties
public class ChatMessage implements Serializable{
    String author, content, date, status;
    boolean click;

    public ChatMessage() {
        click = false;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ChatMessage(String author, String content, String date, String status) {
        this.author = author;
        this.content = content;
        this.date = date;
        this.status = status;
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> results = new HashMap<>();
        results.put("author",author);
        results.put("content",content);
        results.put("date",date);
        results.put("status",status);
        results.put("click", click);
        return results;
    }
}
