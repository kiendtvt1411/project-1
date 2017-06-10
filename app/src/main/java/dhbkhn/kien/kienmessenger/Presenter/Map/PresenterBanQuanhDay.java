package dhbkhn.kien.kienmessenger.Presenter.Map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Map.XuLyLayBanQuanhDay;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.BanQuanhDay;
import dhbkhn.kien.kienmessenger.View.Map.IViewBanQuanhDay;

/**
 * Created by kiend on 10/10/2016.
 */
public class PresenterBanQuanhDay implements IPresenterBanQuanhDay {
    IViewBanQuanhDay iViewBanQuanhDay;
    XuLyLayBanQuanhDay xuLyLayBanQuanhDay;

    public PresenterBanQuanhDay(IViewBanQuanhDay iViewBanQuanhDay) {
        this.iViewBanQuanhDay = iViewBanQuanhDay;
        xuLyLayBanQuanhDay = new XuLyLayBanQuanhDay();
    }

    @Override
    public void layDanhSachBanQuanhDay(LatLng tamBanKinh) {
        List<BanQuanhDay>dsBQD = xuLyLayBanQuanhDay.layRaBanQuanhDay(tamBanKinh);
        if (dsBQD.size() > 0) {
            iViewBanQuanhDay.hienThiDanhSachBanQuanhDay(dsBQD);
        }
    }
}
