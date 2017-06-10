package dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentBaiVietDoanhNghiep;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentNoiBatDoanhNghiep;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentTourDoanhNghiep;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentTrangChuDoanhNghiep;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment.FragmentVideoDoanhNghiep;

/**
 * Created by kiend on 11/3/2016.
 */
public class ViewPagerAdapterDoanhNghiep extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();
    Activity activity;

    public ViewPagerAdapterDoanhNghiep(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
        fragmentList.add(new FragmentTrangChuDoanhNghiep());
        fragmentList.add(new FragmentNoiBatDoanhNghiep());
        fragmentList.add(new FragmentTourDoanhNghiep());
        fragmentList.add(new FragmentBaiVietDoanhNghiep());
        fragmentList.add(new FragmentVideoDoanhNghiep());

        listTitle.add("Trang chủ");
        listTitle.add("Nổi bật");
        listTitle.add("Tour");
        listTitle.add("Bài đăng");
        listTitle.add("Ảnh");
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}
