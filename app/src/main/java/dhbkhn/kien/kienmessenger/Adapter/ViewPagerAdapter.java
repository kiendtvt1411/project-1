package dhbkhn.kien.kienmessenger.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentChatDoi;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentChatNhom;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentDanhBa;

/**
 * Created by kiend on 9/22/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentChatDoi fragmentChatDoi = new FragmentChatDoi();
                return fragmentChatDoi;
            case 1:
                FragmentChatNhom fragmentChatNhom = new FragmentChatNhom();
                return fragmentChatNhom;
            case 2:
                FragmentDanhBa fragmentDanhBa = new FragmentDanhBa();
                return fragmentDanhBa;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Lịch sử";
            case 1:
                return "Nhóm";
            case 2:
                return "Danh bạ";
            default:
                return null;
        }
    }
}
