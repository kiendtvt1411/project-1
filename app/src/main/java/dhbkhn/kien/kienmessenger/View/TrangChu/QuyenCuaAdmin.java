package dhbkhn.kien.kienmessenger.View.TrangChu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import dhbkhn.kien.kienmessenger.Adapter.QuyenAdmin.ViewPagerQuyenAdmin;
import dhbkhn.kien.kienmessenger.R;

public class QuyenCuaAdmin extends AppCompatActivity {
    TabLayout tabLayoutAdmin;
    ViewPager viewPagerAdmin;
    ViewPagerQuyenAdmin adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quyen_cua_amin);
        addControls();
        addEvents();
    }

    private void addControls() {
        tabLayoutAdmin = (TabLayout) findViewById(R.id.tabLayoutQuyenAdmin);
        viewPagerAdmin = (ViewPager) findViewById(R.id.viewPagerQuyenAdmin);
        adapterViewPager = new ViewPagerQuyenAdmin(getSupportFragmentManager());
        viewPagerAdmin.setAdapter(adapterViewPager);
        tabLayoutAdmin.setupWithViewPager(viewPagerAdmin);
    }

    private void addEvents() {
    }
}
