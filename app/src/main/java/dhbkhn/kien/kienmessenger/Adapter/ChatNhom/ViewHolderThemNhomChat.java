package dhbkhn.kien.kienmessenger.Adapter.ChatNhom;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 9/29/2016.
 */
public class ViewHolderThemNhomChat extends RecyclerView.ViewHolder {
    public CardView cvMoiChatNhom;
    public CheckBox chkMoiChatNhom;
    public ImageView imgAvatarMoiChatNhom, imgOnlineMoiChatNhom;
    public TextView tvUsernameMoiChatNhom, tvEmailMoiChatNhom;

    public ViewHolderThemNhomChat(View itemView) {
        super(itemView);
        cvMoiChatNhom = (CardView) itemView.findViewById(R.id.cvMoiChatNhom);
        chkMoiChatNhom = (CheckBox) itemView.findViewById(R.id.chkMoiChatNhom);
        imgAvatarMoiChatNhom = (ImageView) itemView.findViewById(R.id.imgAvatarMoiChatNhom);
        imgOnlineMoiChatNhom = (ImageView) itemView.findViewById(R.id.imgOnlineMoiChatNhom);
        tvUsernameMoiChatNhom = (TextView) itemView.findViewById(R.id.tvUsernameMoiChatNhom);
        tvEmailMoiChatNhom = (TextView) itemView.findViewById(R.id.tvEmailMoiChatNhom);
    }
}
