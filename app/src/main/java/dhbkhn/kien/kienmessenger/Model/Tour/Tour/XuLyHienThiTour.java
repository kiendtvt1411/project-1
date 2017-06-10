package dhbkhn.kien.kienmessenger.Model.Tour.Tour;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;

/**
 * Created by kiend on 11/11/2016.
 */
public class XuLyHienThiTour {
    public List<String> layRaListKeyDiaDiem(TourDuLich tourDuLich) {
        List<String> dsKey = new ArrayList<>();
        dsKey.addAll(tourDuLich.getList_lo_trinh());
        Log.d("soooool", dsKey.size() + "");
        return dsKey;
    }
}
