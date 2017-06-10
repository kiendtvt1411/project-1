package dhbkhn.kien.kienmessenger.View.TrangChu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.ViewPagerAdapterGroupThamGia;
import dhbkhn.kien.kienmessenger.R;

public class DanhSachNhomDaThamGia extends AppCompatActivity {
    TabLayout tabLayoutGroup;
    ViewPager viewPagerGroup;
    ViewPagerAdapterGroupThamGia viewPagerAdapterGroupThamGia;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nhom_da_tham_gia);
        setTitle("Tour du lịch");
        addControls();
        addEvent();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayoutGroup = (TabLayout) findViewById(R.id.tabLayoutGroup);
        viewPagerGroup = (ViewPager) findViewById(R.id.viewPagerGroup);
        viewPagerAdapterGroupThamGia = new ViewPagerAdapterGroupThamGia(getSupportFragmentManager(), this);
        viewPagerGroup.setAdapter(viewPagerAdapterGroupThamGia);
        viewPagerAdapterGroupThamGia.notifyDataSetChanged();
        tabLayoutGroup.setupWithViewPager(viewPagerGroup);
    }

    private void addEvent() {

    }
}
