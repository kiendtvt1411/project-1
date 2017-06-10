package dhbkhn.kien.kienmessenger.Presenter.Status.Comment;

import dhbkhn.kien.kienmessenger.Model.Object.Comment;
import dhbkhn.kien.kienmessenger.Model.Status.Comment.XuLyDangComment;
import dhbkhn.kien.kienmessenger.View.Status.Comment.IViewDangComment;

/**
 * Created by kiend on 10/21/2016.
 */
public class PresenterDangComment implements IPresenterDangComment {
    IViewDangComment iViewDangComment;
    XuLyDangComment xuLyDangComment;

    public PresenterDangComment(IViewDangComment iViewDangComment) {
        this.iViewDangComment = iViewDangComment;
        xuLyDangComment = new XuLyDangComment();
    }

    @Override
    public String layKeyDangComment(String key_status) {
        String keyComment = xuLyDangComment.layKeyComment(key_status);
        return keyComment;
    }

    @Override
    public void xuLyDangComment(String key_status, String key, Comment comment) {
        xuLyDangComment.themCommentTreTrau(key_status,key,comment);
        iViewDangComment.dangCommentThanhCong();
    }
}
