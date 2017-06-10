package dhbkhn.kien.kienmessenger.Presenter.Status;

import dhbkhn.kien.kienmessenger.Model.Status.XuLyDangStatusCaNhan;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.View.Status.IViewDangStatus;

/**
 * Created by kiend on 10/19/2016.
 */
public class PresenterDangStatus implements IPresenterDangStatus {
    IViewDangStatus iViewDangStatus;
    XuLyDangStatusCaNhan xuLyDangStatusCaNhan;

    public PresenterDangStatus(IViewDangStatus iViewDangStatus) {
        this.iViewDangStatus = iViewDangStatus;
        xuLyDangStatusCaNhan = new XuLyDangStatusCaNhan();
    }

    @Override
    public String layKeyDangStatus(String nguoidung) {
        String key = xuLyDangStatusCaNhan.layKeyStatusCaNhan(nguoidung);
        return key;
    }

    @Override
    public void dangStatusCaNhan(String nguoidung, String key, Status status) {
        xuLyDangStatusCaNhan.themStatusCaNhan(nguoidung,key,status);
        iViewDangStatus.dangStatusThanhCong();
    }
}
