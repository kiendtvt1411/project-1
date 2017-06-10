package dhbkhn.kien.kienmessenger.Model.Tour.Tour;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;

/**
 * Created by kiend on 11/9/2016.
 */
public class XuLyThemTour {
    public String layKeyThemTour(String keyCongTy) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String keyTour = ref.child("tour").child(keyCongTy).push().getKey();
        return keyTour;
    }

    public void themTour(String keyCongTy, String keyTour, TourDuLich tourDuLich) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> hmUpdate = new HashMap<>();
        Map<String, Object> hmTour = tourDuLich.toMap();
        hmUpdate.put("/tour/" + keyCongTy + "/" + keyTour, hmTour);
        ref.updateChildren(hmUpdate);
    }
}
