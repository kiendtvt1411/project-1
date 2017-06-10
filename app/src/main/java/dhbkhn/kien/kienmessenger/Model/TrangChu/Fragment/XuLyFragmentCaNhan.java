package dhbkhn.kien.kienmessenger.Model.TrangChu.Fragment;

import android.widget.TextView;

/**
 * Created by kiend on 10/19/2016.
 */
public class XuLyFragmentCaNhan {
    public void layRaUsername(String nguoidung, TextView tv) {
        AsyncTaskCaNhan task = new AsyncTaskCaNhan(tv);
        task.execute(nguoidung);
    }
}
