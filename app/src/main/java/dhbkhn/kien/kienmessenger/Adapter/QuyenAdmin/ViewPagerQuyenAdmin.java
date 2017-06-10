package dhbkhn.kien.kienmessenger.Adapter.QuyenAdmin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentQuyenAdmin.FragmentAdminThemDiaDiem;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentQuyenAdmin.FragmentKiemDuyetYeuCau;

/**
 * Created by kiend on 11/5/2016.
 */
public class ViewPagerQuyenAdmin extends FragmentStatePagerAdapter {
    List<Fragment>dsFragment = new ArrayList<>();
    List<String>dsTitle = new ArrayList<>();

    public ViewPagerQuyenAdmin(FragmentManager fm) {
        super(fm);
        dsFragment.add(new FragmentKiemDuyetYeuCau());
        dsFragment.add(new FragmentAdminThemDiaDiem());

        dsTitle.add("Duyệt yêu cầu");
        dsTitle.add("Thêm địa điểm");
    }

    @Override
    public Fragment getItem(int position) {
        return dsFragment.get(position);
    }

    @Override
    public int getCount() {
        return dsFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dsTitle.get(position);
    }
}
