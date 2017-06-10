package dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentNhomBanQuanLy;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentNhomBanThamGia;

/**
 * Created by kiend on 11/4/2016.
 */
public class ViewPagerAdapterGroupThamGia extends FragmentStatePagerAdapter {
    Activity activity;
    List<Fragment> dsFragment = new ArrayList<>();
    List<String> dsTitle = new ArrayList<>();

    public ViewPagerAdapterGroupThamGia(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
        dsFragment.add(new FragmentNhomBanThamGia());
        dsFragment.add(new FragmentNhomBanQuanLy());

        dsTitle.add("Đã tham gia");
        dsTitle.add("Bạn quản lý");
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
