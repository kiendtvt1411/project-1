package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import dhbkhn.kien.kienmessenger.Adapter.TrangChu.AdapterThongTinDuLich;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/26/2016.
 */
public class FragmentThongTinDuLich extends Fragment {
    public HorizontalInfiniteCycleViewPager infiniteCycleViewPager;
    private AdapterThongTinDuLich adapterThongTinDuLich;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_thong_tin_du_lich, container, false);
        infiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) row.findViewById(R.id.hicvp);
        adapterThongTinDuLich = new AdapterThongTinDuLich(getActivity().getSupportFragmentManager());
        infiniteCycleViewPager.setAdapter(adapterThongTinDuLich);
        infiniteCycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        infiniteCycleViewPager.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.facebook));
                        infiniteCycleViewPager.notifyDataSetChanged();
                        Log.d("myLog", position + "");
                        break;
                    case 1:
                        infiniteCycleViewPager.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                        infiniteCycleViewPager.notifyDataSetChanged();
                        Log.d("myLog", position + "");
                        break;
                    case 2:
                        infiniteCycleViewPager.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorYellow));
                        infiniteCycleViewPager.notifyDataSetChanged();
                        Log.d("myLog", position + "");
                        break;
                    case 3:
                        infiniteCycleViewPager.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.green));
                        infiniteCycleViewPager.notifyDataSetChanged();
                        Log.d("myLog", position + "");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapterThongTinDuLich.notifyDataSetChanged();
        infiniteCycleViewPager.setScrollDuration(1000);
        infiniteCycleViewPager.setMediumScaled(false);

        infiniteCycleViewPager.setMaxPageScale(0.8F);
        infiniteCycleViewPager.setMinPageScale(0.5F);
        infiniteCycleViewPager.setCenterPageScaleOffset(50.0F);
        infiniteCycleViewPager.setMinPageScaleOffset(25.0F);
        return row;
    }
}
