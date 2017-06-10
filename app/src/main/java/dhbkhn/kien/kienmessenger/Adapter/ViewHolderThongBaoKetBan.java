package dhbkhn.kien.kienmessenger.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/5/2016.
 */
public class ViewHolderThongBaoKetBan extends RecyclerView.ViewHolder {
    public LinearLayout llThongBao, llChuaLaBanBe, llDaLaBanBe;
    public TextView tvThongBao, tvThongBaoDaLaBanBe;
    public ImageView imgThongBao;
    public Button btnOKKetBan, btnCancelKetBan;

    public ViewHolderThongBaoKetBan(View itemView) {
        super(itemView);
        llThongBao = (LinearLayout) itemView.findViewById(R.id.llThongBao);
        llDaLaBanBe = (LinearLayout) itemView.findViewById(R.id.llDaLaBanBe);
        llChuaLaBanBe = (LinearLayout) itemView.findViewById(R.id.llChuaLaBanBe);
        tvThongBao = (TextView) itemView.findViewById(R.id.tvThongBao);
        tvThongBaoDaLaBanBe = (TextView) itemView.findViewById(R.id.tvThongBaoDaLaBanBe);
        imgThongBao = (ImageView) itemView.findViewById(R.id.imgThongBao);
        btnOKKetBan = (Button) itemView.findViewById(R.id.btnOKKetBan);
        btnCancelKetBan = (Button) itemView.findViewById(R.id.btnCancelKetBan);
    }
}
