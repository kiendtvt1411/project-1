package dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/4/2016.
 */
public class ViewHolderPageNhom extends RecyclerView.ViewHolder {
    public CardView cvPageNhomThamGia;
    public ImageView imgItemAnhBiaPageNhom, imgItemAvatarPageNhom;
    public TextView tvItemTenPageNhom, tvItemDiaChiPageNhom;
    public ViewHolderPageNhom(View itemView) {
        super(itemView);
        cvPageNhomThamGia = (CardView) itemView.findViewById(R.id.cvPageNhomThamGia);
        imgItemAnhBiaPageNhom = (ImageView) itemView.findViewById(R.id.imgItemAnhBiaPageNhom);
        imgItemAvatarPageNhom = (ImageView) itemView.findViewById(R.id.imgItemAvatarPageNhom);
        tvItemTenPageNhom = (TextView) itemView.findViewById(R.id.tvItemTenPageNhom);
        tvItemDiaChiPageNhom = (TextView) itemView.findViewById(R.id.tvItemDiaChiPageNhom);
    }
}
