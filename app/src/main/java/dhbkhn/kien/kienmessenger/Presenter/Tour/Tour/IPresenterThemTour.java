package dhbkhn.kien.kienmessenger.Presenter.Tour.Tour;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;

/**
 * Created by kiend on 11/10/2016.
 */
public interface IPresenterThemTour {
    String layKeyTour(String keyCongTy);
    void themTour(String keyCongTy, String keyTour, TourDuLich tourDuLich);
}
