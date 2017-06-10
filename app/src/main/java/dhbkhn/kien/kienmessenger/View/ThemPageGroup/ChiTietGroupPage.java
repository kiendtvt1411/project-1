package dhbkhn.kien.kienmessenger.View.ThemPageGroup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.ViewPagerAdapterDoanhNghiep;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.PageDoanhNghiep;
import dhbkhn.kien.kienmessenger.R;

public class ChiTietGroupPage extends AppCompatActivity {
    TabLayout tabDoanhNghiep;
    ViewPager viewPagerDoanhNghiep;
    ViewPagerAdapterDoanhNghiep viewPagerAdapterDoanhNghiep;
    ImageView imgAnhBiaDoanhNghiep, imgAvatarDoanhNghiep;
    TextView tvTenTrangDoanhNghiep, tvViTriDoanhNghiep;
    PageDoanhNghiep pageDoanhNghiep = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_group_page);
        addControls();
        addEvents();
    }

    private void addControls() {
        tabDoanhNghiep = (TabLayout) findViewById(R.id.tabDoanhNghiep);
        viewPagerDoanhNghiep = (ViewPager) findViewById(R.id.viewPagerDoanhNghiep);
        viewPagerAdapterDoanhNghiep = new ViewPagerAdapterDoanhNghiep(getSupportFragmentManager(), this);
        viewPagerDoanhNghiep.setAdapter(viewPagerAdapterDoanhNghiep);
        tabDoanhNghiep.setupWithViewPager(viewPagerDoanhNghiep);

        imgAnhBiaDoanhNghiep = (ImageView) findViewById(R.id.imgAnhBiaDoanhNghiep);
        imgAvatarDoanhNghiep = (ImageView) findViewById(R.id.imgAvatarDoanhNghiep);
        tvTenTrangDoanhNghiep = (TextView) findViewById(R.id.tvTenTrangDoanhNghiep);
        tvViTriDoanhNghiep = (TextView) findViewById(R.id.tvViTriDoanhNghiep);

        pageDoanhNghiep = (PageDoanhNghiep) getIntent().getSerializableExtra("page");
    }

    private void addEvents() {
        if (pageDoanhNghiep != null) {
            hienThiAnh(pageDoanhNghiep.getAnh_bia_url_cty(), imgAnhBiaDoanhNghiep);
            hienThiAnh(pageDoanhNghiep.getAvatar_url_cty(), imgAvatarDoanhNghiep);
            tvTenTrangDoanhNghiep.setText(pageDoanhNghiep.getTen_cty());
            tvViTriDoanhNghiep.setText(pageDoanhNghiep.getDiachi_cty());
        }
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
