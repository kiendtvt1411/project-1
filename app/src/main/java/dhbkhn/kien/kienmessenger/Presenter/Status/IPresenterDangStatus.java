package dhbkhn.kien.kienmessenger.Presenter.Status;

import dhbkhn.kien.kienmessenger.Model.Object.Status;

/**
 * Created by kiend on 10/19/2016.
 */
public interface IPresenterDangStatus {
    String layKeyDangStatus(String nguoidung);

    void dangStatusCaNhan(String nguoidung, String key, Status status);
}
