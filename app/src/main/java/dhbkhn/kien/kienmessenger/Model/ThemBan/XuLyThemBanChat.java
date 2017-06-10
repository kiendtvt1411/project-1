package dhbkhn.kien.kienmessenger.Model.ThemBan;

import java.util.concurrent.ExecutionException;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;

/**
 * Created by kiend on 10/11/2016.
 */
public class XuLyThemBanChat {
    public DuLieuThongBao layDuLieuThongBao(String friend) {
        DuLieuThongBao dl = new DuLieuThongBao();
        AsyncThemBanChat them = new AsyncThemBanChat();
        them.execute(friend);

        try {
            dl = them.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return dl;
    }
}
