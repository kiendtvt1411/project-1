package dhbkhn.kien.kienmessenger.Model.Status;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.Status;

/**
 * Created by kiend on 10/19/2016.
 */
public class XuLyDangStatusCaNhan {
    public String layKeyStatusCaNhan(String nguoidung) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String key = ref.child("status").child(nguoidung).push().getKey();
        return key;
    }

    public void themStatusCaNhan(String nguoidung, final String key, Status status) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final Map<String,Object>hmS = status.toMap();
        final Map<String, Object> hm = new HashMap<>();
        hm.put("/status/" + nguoidung + "/cua_toi/" + key, hmS);
        ref.updateChildren(hm);
        ref.child("users").child(nguoidung).child("friendlist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Friend fr = post.getValue(Friend.class);
                    hm.put("/status/" + fr.getUsername_friend() + "/cua_ban_be_toi/" + key, hmS);
                    ref.updateChildren(hm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
