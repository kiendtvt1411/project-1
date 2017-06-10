package dhbkhn.kien.kienmessenger.Presenter.TrangChu.FragmentCaNhan;

import android.widget.TextView;

import dhbkhn.kien.kienmessenger.Model.TrangChu.Fragment.XuLyFragmentCaNhan;

/**
 * Created by kiend on 10/18/2016.
 */
public class PresenterCaNhan implements IPresenterCaNhan {
    XuLyFragmentCaNhan xuLyFragmentCaNhan;

    public PresenterCaNhan() {
        xuLyFragmentCaNhan = new XuLyFragmentCaNhan();
    }

    @Override
    public void layRaUsernameTrangCaNhan(String nguoidung, TextView textView) {
        xuLyFragmentCaNhan.layRaUsername(nguoidung, textView);
    }
}
