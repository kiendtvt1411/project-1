package dhbkhn.kien.kienmessenger.Adapter.TrangChu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich.FragmentDacSan;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich.FragmentDiaDiem;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich.FragmentKhachSan;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich.FragmentLeHoi;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentThongTinDuLich.FragmentTourLuHanh;

/**
 * Created by kiend on 11/26/2016.
 */
public class AdapterThongTinDuLich extends FragmentStatePagerAdapter {
    public AdapterThongTinDuLich(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentDiaDiem fragmentDiaDiem = new FragmentDiaDiem();
                return fragmentDiaDiem;
            case 1:
                FragmentDacSan fragmentDacSan = new FragmentDacSan();
                return fragmentDacSan;
            case 2:
                FragmentLeHoi fragmentLeHoi = new FragmentLeHoi();
                return fragmentLeHoi;
            case 3:
                FragmentKhachSan fragmentKhachSan = new FragmentKhachSan();
                return fragmentKhachSan;
            case 4:
                FragmentTourLuHanh fragmentTourLuHanh = new FragmentTourLuHanh();
                return fragmentTourLuHanh;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
