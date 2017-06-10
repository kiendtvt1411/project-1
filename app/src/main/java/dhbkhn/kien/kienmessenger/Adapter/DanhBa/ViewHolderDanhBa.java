package dhbkhn.kien.kienmessenger.Adapter.DanhBa;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 9/22/2016.
 */
public class ViewHolderDanhBa extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public TextView tvUsernameDanhBa, tvAvatarDanhBa;
    public ImageView imgOnlineDanhBa, imgAvaDanhBa;
    public CardView itemDanhBa;
    public LinearLayout llItemDanhBa;
    public ViewHolderDanhBa(View itemView) {
        super(itemView);
        itemDanhBa = (CardView) itemView.findViewById(R.id.itemDanhBa);
        tvUsernameDanhBa = (TextView) itemView.findViewById(R.id.tvUsernameDanhBa);
        tvAvatarDanhBa = (TextView) itemView.findViewById(R.id.tvAvatarDanhBa);
        imgOnlineDanhBa = (ImageView) itemView.findViewById(R.id.imgOnlineDanhBa);
        imgAvaDanhBa = (ImageView) itemView.findViewById(R.id.imgAvatarDanhBa);
        llItemDanhBa = (LinearLayout) itemView.findViewById(R.id.llItemDanhBa);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }
}
