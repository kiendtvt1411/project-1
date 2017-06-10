package dhbkhn.kien.kienmessenger.Presenter.TrangChu.CapNhatNguoiDung;

import dhbkhn.kien.kienmessenger.Model.TrangChu.XuLyCapNhatNguoiDung;
import dhbkhn.kien.kienmessenger.View.TrangChu.ITrangChu;

/**
 * Created by kiend on 10/18/2016.
 */
public class PresenterTrangChu implements IPresenterTrangChu {
    ITrangChu iTrangChu;
    XuLyCapNhatNguoiDung xuLyCapNhatNguoiDung;

    public PresenterTrangChu(ITrangChu iTrangChu) {
        this.iTrangChu = iTrangChu;
        xuLyCapNhatNguoiDung = new XuLyCapNhatNguoiDung();
    }

    @Override
    public void xuLyHienThiAvatar(String nguoidung) {
        xuLyCapNhatNguoiDung.capNhatAvatarNguoiDung(nguoidung);
        iTrangChu.capNhatAvatar();
    }
}
