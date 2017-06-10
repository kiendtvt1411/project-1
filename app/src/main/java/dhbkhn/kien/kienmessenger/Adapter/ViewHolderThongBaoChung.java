package dhbkhn.kien.kienmessenger.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 12/1/2016.
 */
public class ViewHolderThongBaoChung extends RecyclerView.ViewHolder{
    public LinearLayout llThongBaoChung;
    public ImageView imgThongBaoChung;
    public TextView tvNoiDungThongBaoChung, tvThoiGianThongBaoChung;

    public ViewHolderThongBaoChung(View itemView) {
        super(itemView);
        llThongBaoChung = (LinearLayout) itemView.findViewById(R.id.llThongBaoChung);
        imgThongBaoChung = (ImageView) itemView.findViewById(R.id.imgThongBaoChung);
        tvNoiDungThongBaoChung = (TextView) itemView.findViewById(R.id.tvNoiDungThongBaoChung);
        tvThoiGianThongBaoChung = (TextView) itemView.findViewById(R.id.tvThoiGianThongBaoChung);
    }
}
