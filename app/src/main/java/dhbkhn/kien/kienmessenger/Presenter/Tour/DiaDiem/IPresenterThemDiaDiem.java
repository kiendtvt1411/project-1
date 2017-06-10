package dhbkhn.kien.kienmessenger.Presenter.Tour.DiaDiem;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.DiaDiemDuLich;

/**
 * Created by kiend on 11/9/2016.
 */
public interface IPresenterThemDiaDiem {
    String layKeyDiaDiem(String loaiDiaDiem);
    void themDiaDiemDuLich(String loaiDiaDiem, String keyDiaDiem, DiaDiemDuLich diaDiemDuLich);
}
