package dhbkhn.kien.kienmessenger.Presenter.Tour.Tour;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.Model.Tour.Tour.XuLyHienThiTour;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour.IViewChiTietTour;

/**
 * Created by kiend on 11/11/2016.
 */
public class PresenterSliderChiTietTour implements IPresenterSliderChiTietTour {
    IViewChiTietTour iViewChiTietTour;
    XuLyHienThiTour xuLyHienThiTour;

    public PresenterSliderChiTietTour(IViewChiTietTour iViewChiTietTour) {
        this.iViewChiTietTour = iViewChiTietTour;
        xuLyHienThiTour = new XuLyHienThiTour();
    }

    @Override
    public void hienThiSlider(TourDuLich tourDuLich) {
        List<String> dsKey = xuLyHienThiTour.layRaListKeyDiaDiem(tourDuLich);
        if (dsKey != null) {
            iViewChiTietTour.showSliderLoTrinh(dsKey);
        }
    }
}
