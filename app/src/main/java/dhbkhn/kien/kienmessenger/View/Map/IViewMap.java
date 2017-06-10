package dhbkhn.kien.kienmessenger.View.Map;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu.DichVu;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu.LoaiDichVu;

/**
 * Created by kiend on 10/9/2016.
 */
public interface IViewMap {
    void hienThiDiaDiemDichVuTheoLoai(List<DichVu>dsDV);
    void hienThiTatCaDiaDiem(List<DichVu> dsDV);
    void hienThiDanhSachLoaiDichVu(List<LoaiDichVu> dsLDV);
}
