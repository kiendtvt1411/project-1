package dhbkhn.kien.kienmessenger.Adapter.Tour;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/5/2016.
 */
public class ViewHolderTour extends RecyclerView.ViewHolder {
    public ImageView imgPhuongTienTour, imgMenuTour, imgAnhBiaTour;
    public TextView tvThoiGianTour, tvLichTrinhTour, tvGiaTour, tvNguoiToChucTour, tvSoDienThoaiTour, tvItemXuatPhatTour;
    public LinearLayout llLienLacTour, llChiTietTour;
    public CardView cvChiTietTour;

    public ViewHolderTour(View itemView) {
        super(itemView);
        llLienLacTour = (LinearLayout) itemView.findViewById(R.id.llLienLacTour);
        llChiTietTour = (LinearLayout) itemView.findViewById(R.id.llChiTietTour);
        imgAnhBiaTour = (ImageView) itemView.findViewById(R.id.imgAnhBiaTour);
        imgPhuongTienTour = (ImageView) itemView.findViewById(R.id.imgPhuongTienTour);
        imgMenuTour = (ImageView) itemView.findViewById(R.id.imgMenuTour);
        tvThoiGianTour = (TextView) itemView.findViewById(R.id.tvThoiGianTour);
        tvLichTrinhTour = (TextView) itemView.findViewById(R.id.tvLichTrinhTour);
        tvGiaTour = (TextView) itemView.findViewById(R.id.tvGiaTour);
        tvNguoiToChucTour = (TextView) itemView.findViewById(R.id.tvNguoiToChucTour);
        tvSoDienThoaiTour = (TextView) itemView.findViewById(R.id.tvSoDienThoaiTour);
        tvItemXuatPhatTour = (TextView) itemView.findViewById(R.id.tvItemXuatPhatTour);
        cvChiTietTour = (CardView) itemView.findViewById(R.id.cvChiTietTour);
    }
}
