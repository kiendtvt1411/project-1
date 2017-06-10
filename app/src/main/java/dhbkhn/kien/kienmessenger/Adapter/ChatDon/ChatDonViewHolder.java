package dhbkhn.kien.kienmessenger.Adapter.ChatDon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 9/29/2016.
 */
public class ChatDonViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llKhungCHat;
    public ImageView imgAvatar;
    public TextView tvUsername, tvLastMess, tvStatus, tvDate, tvSoLuongTinNhanChuaXem;

    public ChatDonViewHolder(View itemView) {
        super(itemView);
        llKhungCHat = (LinearLayout) itemView.findViewById(R.id.llKhungChat);
        imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        tvLastMess = (TextView) itemView.findViewById(R.id.tvLastMess);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvSoLuongTinNhanChuaXem = (TextView) itemView.findViewById(R.id.tvSoLuongTinNhanChuaXem);
    }
}
