package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.Tour.ViewPagerAdapterSlider;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.Presenter.Tour.Tour.PresenterSliderChiTietTour;
import dhbkhn.kien.kienmessenger.R;

public class ChiTietTour extends AppCompatActivity implements IViewChiTietTour{
    TextView tvTenChiTietTour,tvGiaChiTietTour, tvTenCongTyDuLichToChucTour, tvThongTinChiTiet;
    ViewPager viewPagerLoTrinhThemTour;
    RecyclerView rvLoTrinhChiTietTour;
    ImageView imgXemThemTour;
    TourDuLich tourDuLich = null;
    List<Fragment> dsFragment;
    LinearLayout llLoTrinhThemTour;
    PresenterSliderChiTietTour presenterSliderChiTietTour;
    ImageView[] imgPoints;
    String tenCongTy = "";
    boolean isExpand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tour);
        addControls();
        addEvents();
    }

    private boolean layDuLieu(){
        tourDuLich = (TourDuLich) getIntent().getSerializableExtra("tour");
        tenCongTy = getIntent().getStringExtra("tencty");
        if (tourDuLich != null) {
            return true;
        }else {
            return false;
        }
    }

    private void addControls() {
        tvTenChiTietTour = (TextView) findViewById(R.id.tvTenChiTietTour);
        tvGiaChiTietTour = (TextView) findViewById(R.id.tvGiaChiTietTour);
        tvTenCongTyDuLichToChucTour = (TextView) findViewById(R.id.tvTenCongTyDuLichToChucTour);
        tvThongTinChiTiet = (TextView) findViewById(R.id.tvThongTinChiTiet);
        imgXemThemTour = (ImageView) findViewById(R.id.imgXemThemTour);
        viewPagerLoTrinhThemTour = (ViewPager) findViewById(R.id.viewPagerLoTrinhThemTour);
        rvLoTrinhChiTietTour = (RecyclerView) findViewById(R.id.rvLoTrinhChiTietTour);
        llLoTrinhThemTour = (LinearLayout) findViewById(R.id.llLoTrinhThemTour);
        rvLoTrinhChiTietTour.setLayoutManager(new LinearLayoutManager(this));
        presenterSliderChiTietTour = new PresenterSliderChiTietTour(this);
    }

    private void addEvents() {
        boolean ktra = layDuLieu();
        if (ktra) {
            tvTenCongTyDuLichToChucTour.setText(tenCongTy);
            if (tourDuLich.getMo_ta().length() > 99) {
                tvThongTinChiTiet.setText(tourDuLich.getMo_ta().substring(0,100));
            }else {
                tvThongTinChiTiet.setText(tourDuLich.getMo_ta().substring(0,tourDuLich.getMo_ta().length()));
            }
            tvTenChiTietTour.setText(tourDuLich.getTen_tour());
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String gia = numberFormat.format(tourDuLich.getGia_tien()).toString();
            tvGiaChiTietTour.setText(gia + " VNĐ");
            presenterSliderChiTietTour.hienThiSlider(tourDuLich);
            imgXemThemTour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpand = !isExpand;
                    if (isExpand) {
                        tvThongTinChiTiet.setText(tourDuLich.getMo_ta());
                        imgXemThemTour.setImageDrawable(getDrawableChiTiet(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    }else {
                        if (tourDuLich.getMo_ta().length() > 99) {
                            tvThongTinChiTiet.setText(tourDuLich.getMo_ta().substring(0,100));
                        }else {
                            tvThongTinChiTiet.setText(tourDuLich.getMo_ta().substring(0,tourDuLich.getMo_ta().length()));
                        }
                        imgXemThemTour.setImageDrawable(getDrawableChiTiet(R.drawable.ic_keyboard_arrow_down_black_24dp));
                    }
                }
            });
        }
    }

    public Drawable getDrawableChiTiet(int idDw) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT > 21) {
            drawable = ContextCompat.getDrawable(this,idDw);
        }else {
            drawable = getResources().getDrawable(idDw);
        }
        return drawable;
    }

    @Override
    public void showSliderLoTrinh(List<String> dsKeyDd) {
        dsFragment = new ArrayList<>();
        int dem = dsKeyDd.size();
        Log.d("dem", dem + "" + dsKeyDd.get(0) + dsKeyDd.get(1));
        for(int i = 0;i<dem;i++) {
            FragmentShowLoTrinh fragmentShowLoTrinh = new FragmentShowLoTrinh();
            Bundle bundle = new Bundle();
            bundle.putString("keyDiaDiem",dsKeyDd.get(i));
            fragmentShowLoTrinh.setArguments(bundle);
            dsFragment.add(fragmentShowLoTrinh);
        }

        ViewPagerAdapterSlider viewPagerAdapter = new ViewPagerAdapterSlider(getSupportFragmentManager(), dsFragment);
        viewPagerLoTrinhThemTour.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

        addPoint(0);

        viewPagerLoTrinhThemTour.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addPoint(int viTriHienTai) {
        imgPoints = new ImageView[dsFragment.size()];
        llLoTrinhThemTour.removeAllViews();
        llLoTrinhThemTour.setGravity(Gravity.CENTER);
        int soluong = dsFragment.size();
        for(int i=0;i<soluong;i++) {
            imgPoints[i] = new ImageView(this);
            if (i == 0 || i == soluong - 1) {
                imgPoints[i].setImageResource(R.drawable.endflag);
            }else {
                imgPoints[i].setImageResource(R.drawable.point);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
            lp.setMargins(10,5,10,5);
            llLoTrinhThemTour.setPadding(5,5,5,5);
//            llLoTrinhThemTour.setBackgroundColor(R.color.transparent_light_black);
            imgPoints[i].setLayoutParams(lp);
            llLoTrinhThemTour.addView(imgPoints[i]);
        }
        if (viTriHienTai == 0 || viTriHienTai == soluong - 1) {
            imgPoints[viTriHienTai].setImageResource(R.drawable.endflag2);
        }else {
            imgPoints[viTriHienTai].setImageResource(R.drawable.point2);
        }
    }
}
