package dhbkhn.kien.kienmessenger.Presenter.Tour.DiaDiem;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.DiaDiemDuLich;
import dhbkhn.kien.kienmessenger.Model.Tour.DiaDiem.XuLyThemDiaDiem;
import dhbkhn.kien.kienmessenger.View.TrangChu.FragmentQuyenAdmin.IViewFragmnetThemDiaDiem;

/**
 * Created by kiend on 11/9/2016.
 */
public class PresenterThemDiaDiem implements IPresenterThemDiaDiem {
    IViewFragmnetThemDiaDiem iViewFragmnetThemDiaDiem;
    XuLyThemDiaDiem xuLyThemDiaDiem;

    public PresenterThemDiaDiem(IViewFragmnetThemDiaDiem iViewFragmnetThemDiaDiem) {
        this.iViewFragmnetThemDiaDiem = iViewFragmnetThemDiaDiem;
        xuLyThemDiaDiem = new XuLyThemDiaDiem();
    }

    @Override
    public String layKeyDiaDiem(String loaiDiaDiem) {
        String key = xuLyThemDiaDiem.layRaKeyDiaDiem(loaiDiaDiem);
        return key;
    }

    @Override
    public void themDiaDiemDuLich(String loaiDiaDiem, String keyDiaDiem, DiaDiemDuLich diaDiemDuLich) {
        xuLyThemDiaDiem.themDiaDiemVanHoa(loaiDiaDiem, keyDiaDiem, diaDiemDuLich);
        iViewFragmnetThemDiaDiem.themDiaDiemThanhCong();
    }
}
