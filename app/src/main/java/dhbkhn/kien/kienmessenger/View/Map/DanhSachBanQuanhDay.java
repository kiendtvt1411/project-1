package dhbkhn.kien.kienmessenger.View.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.AdapterBanQuanhDay;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.BanQuanhDay;
import dhbkhn.kien.kienmessenger.Presenter.Map.PresenterBanQuanhDay;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

public class DanhSachBanQuanhDay extends AppCompatActivity implements IViewBanQuanhDay{
    RecyclerView rvBanQuanhDay;
    Button btnLocBanQuanhDay, btnSapXepBanQuanhDay;
    AdapterBanQuanhDay adapter;
    List<BanQuanhDay>dsBanQuanhDay;
    PresenterBanQuanhDay presenterBanQuanhDay;
    String nguoidung="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_ban_quanh_day);
        addControls();
        addEvents();
    }

    private void addControls() {
        rvBanQuanhDay = (RecyclerView) findViewById(R.id.rvBanQuanhDay);
        btnLocBanQuanhDay = (Button) findViewById(R.id.btnLocBanQuanhDay);
        btnSapXepBanQuanhDay = (Button) findViewById(R.id.btnSapXepBanQuanhDay);

        nguoidung = DangNhapMessenger.nguoidung;

        dsBanQuanhDay = new ArrayList<>();
        rvBanQuanhDay.setLayoutManager(new LinearLayoutManager(DanhSachBanQuanhDay.this));
        adapter = new AdapterBanQuanhDay(DanhSachBanQuanhDay.this,dsBanQuanhDay,nguoidung);
        rvBanQuanhDay.setAdapter(adapter);
        presenterBanQuanhDay = new PresenterBanQuanhDay(this);
        presenterBanQuanhDay.layDanhSachBanQuanhDay(new LatLng(21.035557, 105.816578));
    }

    private void addEvents() {
        btnLocBanQuanhDay.setOnClickListener(myClick);
        btnSapXepBanQuanhDay.setOnClickListener(myClick);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btnLocBanQuanhDay:

                    break;
                case R.id.btnSapXepBanQuanhDay:

                    break;
            }
        }
    };

    @Override
    public void hienThiDanhSachBanQuanhDay(List<BanQuanhDay> dsBQD) {
        dsBanQuanhDay.addAll(dsBQD);
        adapter.notifyDataSetChanged();
    }
}
