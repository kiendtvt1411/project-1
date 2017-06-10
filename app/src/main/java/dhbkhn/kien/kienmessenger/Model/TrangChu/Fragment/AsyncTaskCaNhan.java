package dhbkhn.kien.kienmessenger.Model.TrangChu.Fragment;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.Model.Object.User;

/**
 * Created by kiend on 10/19/2016.
 */
public class AsyncTaskCaNhan extends AsyncTask<String, User, Void> {
    TextView tv;

    public AsyncTaskCaNhan(TextView tv) {
        this.tv = tv;
    }

    @Override
    protected Void doInBackground(String... params) {
        String nguoidung = params[0];
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        data.child("users").child(nguoidung).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                publishProgress(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    protected void onProgressUpdate(User... values) {
        super.onProgressUpdate(values);
        User nguoidung = values[0];
        tv.setText(nguoidung.getUsername());
    }
}
