package dhbkhn.kien.kienmessenger.Model.TrangChu;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 12/9/2016.
 */
public class TrangChuService extends Service {
    DatabaseReference mDatabase;
    NotificationManager notificationManager;
    String nguoidung;
    public static final int NOTI_ID = 12345;

    @Override
    public void onCreate() {
        super.onCreate();
        nguoidung = DangNhapMessenger.nguoidung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDatabase.child("notification").child(nguoidung).child("ket_ban").addValueEventListener(myValue);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            if (notificationManager != null) {
                notificationManager.cancel(NOTI_ID);
            }
        } catch (Exception ex) {
            Log.e("ex", ex.toString());
        }
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void xemNoti(String friend){
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_kien_messenger);
        String servName = Context.NOTIFICATION_SERVICE;
        notificationManager = (NotificationManager)getSystemService (servName);

        Intent intent = new Intent(this, TrangChu.class);
        intent.putExtra("callerIntent", "main");
        intent.putExtra("notificationID", NOTI_ID);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder baseNotification = new Notification.Builder(this)
                .setContentTitle(friend + " đã gửi lời kết bạn cho bạn")
                .setTicker(friend + " đã gửi lời kết bạn cho bạn")
                .setSmallIcon(R.drawable.icon_kien_messenger)
                .setLargeIcon(myBitmap)
                .setLights(0xffcc00, 1000, 500)
                .setContentIntent(pIntent);

        Notification noti = new Notification.InboxStyle(baseNotification)
                .addLine("Bạn có muốn nhận lời kết bạn hay không???")
                .build();

        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTI_ID, noti);
    }

    private ValueEventListener myValue = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot post: dataSnapshot.getChildren()){
                dhbkhn.kien.kienmessenger.Model.Object.Notification noti = post.getValue(dhbkhn.kien.kienmessenger.Model.Object.Notification.class);
                if (noti != null && !noti.isConsequence()) {
                    xemNoti(noti.getAuthor());
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
