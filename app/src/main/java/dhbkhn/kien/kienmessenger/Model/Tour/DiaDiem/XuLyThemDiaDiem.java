package dhbkhn.kien.kienmessenger.Model.Tour.DiaDiem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Tour.ChiDeShow;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.DiaDiemDuLich;

/**
 * Created by kiend on 11/9/2016.
 */
public class XuLyThemDiaDiem {
    public String layRaKeyDiaDiem(String loaiDiaDiem){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String key = ref.child("dia_diem").child(loaiDiaDiem).push().getKey();
        return key;
    }

    public void themDiaDiemVanHoa(String loaiDiaDiem, String keyDiaDiem, DiaDiemDuLich diaDiemDuLich){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> hmUpdate = new HashMap<>();
        Map<String, Object> hmDD = diaDiemDuLich.toMap();
        hmUpdate.put("/dia_diem/" + loaiDiaDiem + "/" + keyDiaDiem, hmDD);
        ChiDeShow chiDeShow = new ChiDeShow();
        chiDeShow.setTen_dd(diaDiemDuLich.getTen_dia_diem());
        chiDeShow.setMota_dd(diaDiemDuLich.getMo_ta());
        chiDeShow.setKey_dd(diaDiemDuLich.getKey_dia_diem());
        if (diaDiemDuLich.getList_hinh_anh_url().size() > 0) {
            chiDeShow.setHinh_dd(diaDiemDuLich.getList_hinh_anh_url().get(0));
        }
        Map<String,Object>hmShow = chiDeShow.toMap();
        hmUpdate.put("/dia_diem/chi_de_hien_thi/" + loaiDiaDiem+"/"+keyDiaDiem, hmShow);
        ref.updateChildren(hmUpdate);
    }
}
