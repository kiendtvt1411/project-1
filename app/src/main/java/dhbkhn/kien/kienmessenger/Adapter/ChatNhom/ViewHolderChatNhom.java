package dhbkhn.kien.kienmessenger.Adapter.ChatNhom;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 9/24/2016.
 */
public class ViewHolderChatNhom extends RecyclerView.ViewHolder{
    public CardView cvKhungChat;
    public ImageView imgAvatarNhom;
    public TextView tvTenNhom,tvNguoiThamGiaNhom;
    public ViewHolderChatNhom(View itemView) {
        super(itemView);
        cvKhungChat = (CardView) itemView.findViewById(R.id.cvKhungChat);
        imgAvatarNhom = (ImageView) itemView.findViewById(R.id.imgAvatarNhom);
        tvTenNhom = (TextView) itemView.findViewById(R.id.tvTenNhom);
        tvNguoiThamGiaNhom = (TextView) itemView.findViewById(R.id.tvNguoiThamGiaNhom);
    }
}
