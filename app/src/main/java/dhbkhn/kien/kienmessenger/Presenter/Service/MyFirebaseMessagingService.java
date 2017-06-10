package dhbkhn.kien.kienmessenger.Presenter.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dhbkhn.kien.kienmessenger.View.ThemBanChat.LoiMoiThemBan;

/**
 * Created by kiend on 9/30/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            hienThiThongBao(remoteMessage.getNotification().getBody());
        }else {
            hienThiThongBao(remoteMessage.getData().get("body"),
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("username"));
        }
    }

    private void hienThiThongBao(String body, String title, String username){
        Intent intent = new Intent(this, LoiMoiThemBan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username",username);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSound(sound)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_dialog_alert);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    private void hienThiThongBao(String body) {
        hienThiThongBao(body,"Kết bạn", "Dich vu");
    }
}
