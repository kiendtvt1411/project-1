package dhbkhn.kien.kienmessenger.Model.ThemBan;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;

/**
 * Created by kiend on 10/11/2016.
 */
public class AsyncThemBanChat extends AsyncTask<String, Void, DuLieuThongBao> {
    @Override
    protected DuLieuThongBao doInBackground(String... params) {
        final DuLieuThongBao duLieuThongBao = new DuLieuThongBao();
        FirebaseDatabase.getInstance().getReference().child("notification").child("statistic")
                .child(params[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DuLieuThongBao dl = dataSnapshot.getValue(DuLieuThongBao.class);
                duLieuThongBao.setFriend(dl.getFriend());
                duLieuThongBao.setTongTB(dl.getTongTB());
                duLieuThongBao.setTbChuaXem(dl.getTbChuaXem());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return duLieuThongBao;
    }

}
