package dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/26/2016.
 */
public class FragmentDacSan extends Fragment {
    private TextView tvTenLoaiThongTinDuLich,tvXemThemThongTinDuLich;
    private ImageView imgThongTinDuLich1,imgThongTinDuLich2,imgThongTinDuLich3,imgThongTinDuLich4,imgThongTinDuLich5, imgRuyBang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.custom_fragment_thong_tin_du_lich, container, false);
        tvTenLoaiThongTinDuLich = (TextView) row.findViewById(R.id.tvTenLoaiThongTinDuLich);
        tvXemThemThongTinDuLich = (TextView) row.findViewById(R.id.tvXemThemThongTinDuLich);
        imgThongTinDuLich1 = (ImageView) row.findViewById(R.id.imgThongTinDuLich1);
        imgThongTinDuLich2 = (ImageView) row.findViewById(R.id.imgThongTinDuLich2);
        imgThongTinDuLich3 = (ImageView) row.findViewById(R.id.imgThongTinDuLich3);
        imgThongTinDuLich4 = (ImageView) row.findViewById(R.id.imgThongTinDuLich4);
        imgThongTinDuLich5 = (ImageView) row.findViewById(R.id.imgThongTinDuLich5);
        imgRuyBang = (ImageView) row.findViewById(R.id.imgRuyBang);
        setUpDuLieu();
        return row;
    }

    private void setUpDuLieu(){
        tvTenLoaiThongTinDuLich.setText("Đặc sản vùng miền");
        tvTenLoaiThongTinDuLich.setBackgroundColor(getContext().getResources().getColor(R.color.colorRed));
        imgRuyBang.setImageResource(R.drawable.ruybang_xanh_la_cay);
        imgThongTinDuLich1.setImageResource(R.drawable.dacsan2);
        imgThongTinDuLich2.setImageResource(R.drawable.dacsan5);
        imgThongTinDuLich3.setImageResource(R.drawable.dacsan3);
        imgThongTinDuLich4.setImageResource(R.drawable.dacsan4);
        imgThongTinDuLich5.setImageResource(R.drawable.dacsan1);
    }
}
