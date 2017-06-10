package dhbkhn.kien.kienmessenger.Model.Status.Comment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Comment;

/**
 * Created by kiend on 10/21/2016.
 */
public class XuLyDangComment {
    public String layKeyComment(String key_status) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String key = ref.child("comment").child(key_status).push().getKey();
        return key;
    }

    public void themCommentTreTrau(String key_status,  String key, Comment comment) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        comment.setKey_comment(key);
        Map<String,Object> hmC = comment.toMap();
        Map<String, Object> hm = new HashMap<>();
        hm.put("/comment/" + key_status + "/" + key, hmC);
        ref.updateChildren(hm);
    }
}
