package dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.TrangChu.DanhSachNhomDaThamGia;

/**
 * Created by kiend on 12/17/2016.
 */
public class FragmentTourLuHanh extends Fragment {
    private TextView tvTenLoaiThongTinDuLich, tvXemThemThongTinDuLich;
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
        tvTenLoaiThongTinDuLich.setText("Tour du lịch");
        tvTenLoaiThongTinDuLich.setBackgroundColor(getContext().getResources().getColor(R.color.orange));
        imgThongTinDuLich1.setImageResource(R.drawable.tour1);
        imgThongTinDuLich2.setImageResource(R.drawable.tour2);
        imgThongTinDuLich3.setImageResource(R.drawable.tour3);
        imgThongTinDuLich4.setImageResource(R.drawable.tour4);
        imgThongTinDuLich5.setImageResource(R.drawable.tour5);
        imgRuyBang.setImageResource(R.drawable.ruybang_cyan);
        tvXemThemThongTinDuLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTiet = new Intent(getContext(), DanhSachNhomDaThamGia.class);
                startActivity(iChiTiet);
            }
        });
    }
}
