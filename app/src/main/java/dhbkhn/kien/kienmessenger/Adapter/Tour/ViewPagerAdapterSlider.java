package dhbkhn.kien.kienmessenger.Adapter.Tour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by kiend on 9/15/2016.
 */
public class ViewPagerAdapterSlider extends FragmentStatePagerAdapter {
    List<Fragment> ds;
    public ViewPagerAdapterSlider(FragmentManager fm, List<Fragment> ds) {
        super(fm);
        this.ds = ds;
    }

    @Override
    public Fragment getItem(int position) {
        return ds.get(position);
    }

    @Override
    public int getCount() {
        return ds.size();
    }
}
