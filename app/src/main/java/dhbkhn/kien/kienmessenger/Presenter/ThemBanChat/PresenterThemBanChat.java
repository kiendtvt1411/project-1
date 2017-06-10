package dhbkhn.kien.kienmessenger.Presenter.ThemBanChat;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.ThemBan.XuLyThemBanChat;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.IViewThemBanChat;

/**
 * Created by kiend on 10/5/2016.
 */
public class PresenterThemBanChat implements IPresenterThemBanChat {
    IViewThemBanChat iViewThemBanChat;
    XuLyThemBanChat xuLyThemBanChat;

    public PresenterThemBanChat(IViewThemBanChat iViewThemBanChat) {
        this.iViewThemBanChat = iViewThemBanChat;
        xuLyThemBanChat = new XuLyThemBanChat();
    }

    @Override
    public void layThongBaoThemBan(String friend) {
        DuLieuThongBao dl = xuLyThemBanChat.layDuLieuThongBao(friend);
        if (dl != null) {
            iViewThemBanChat.hienThiSoLuongThongBaoBanChat(dl);
        }
    }
}
