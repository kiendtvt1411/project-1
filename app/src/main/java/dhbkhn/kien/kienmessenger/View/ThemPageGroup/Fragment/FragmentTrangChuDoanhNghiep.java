package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/3/2016.
 */
public class FragmentTrangChuDoanhNghiep extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_trang_chu_doanh_nghiep, container, false);
        return row;
    }
}
