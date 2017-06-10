package dhbkhn.kien.kienmessenger.Presenter.Tour.Tour;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.Model.Tour.Tour.XuLyThemTour;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour.IViewThemTourMoi;

/**
 * Created by kiend on 11/10/2016.
 */
public class PresenterThemTour implements IPresenterThemTour{
    IViewThemTourMoi iViewThemTourMoi;
    XuLyThemTour xuLyThemTour;

    public PresenterThemTour(IViewThemTourMoi iViewThemTourMoi) {
        this.iViewThemTourMoi = iViewThemTourMoi;
        xuLyThemTour = new XuLyThemTour();
    }

    @Override
    public String layKeyTour(String keyCongTy) {
        String keyTOur = xuLyThemTour.layKeyThemTour(keyCongTy);
        return keyTOur;
    }

    @Override
    public void themTour(String keyCongTy, String keyTour, TourDuLich tourDuLich) {
        xuLyThemTour.themTour(keyCongTy,keyTour,tourDuLich);
        iViewThemTourMoi.themTourThanhCong();
    }
}
