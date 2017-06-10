package dhbkhn.kien.kienmessenger.View.TrangChu.FragmentQuyenAdmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/5/2016.
 */
public class FragmentKiemDuyetYeuCau extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_admin_kiem_duyet, container, false);
        return row;
    }
}
