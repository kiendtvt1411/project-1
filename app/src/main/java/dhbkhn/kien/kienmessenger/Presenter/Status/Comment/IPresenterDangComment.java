package dhbkhn.kien.kienmessenger.Presenter.Status.Comment;

import dhbkhn.kien.kienmessenger.Model.Object.Comment;

/**
 * Created by kiend on 10/21/2016.
 */
public interface IPresenterDangComment {
    String layKeyDangComment(String key_status);

    void xuLyDangComment(String key_status, String key, Comment comment);
}
