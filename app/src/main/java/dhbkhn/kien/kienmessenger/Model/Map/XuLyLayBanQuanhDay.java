package dhbkhn.kien.kienmessenger.Model.Map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dhbkhn.kien.kienmessenger.Model.KetNoiMang.ConnectInternet;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.BanQuanhDay;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 10/10/2016.
 */
public class XuLyLayBanQuanhDay {
    public List<BanQuanhDay> layRaBanQuanhDay(LatLng tamDuongTron){
        List<BanQuanhDay> dsBQD = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hmHam = new HashMap<>();
        hmHam.put("ham", "LayDanhSachNguoiDung");
        HashMap<String, String> hmMaLoaiND = new HashMap<>();
        hmMaLoaiND.put("maloaind", String.valueOf(2));
        attrs.add(hmHam);
        attrs.add(hmMaLoaiND);

        ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_NAME_URL, attrs);
        connectInternet.execute();

        try {
            String jsonData = connectInternet.get();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("DANHSACHNGUOIDUNG");
            int count = jsonArray.length();
            for(int i = 0;i<count;i++) {
                BanQuanhDay banQuanhDay = new BanQuanhDay();
                JSONObject data = jsonArray.getJSONObject(i);
                banQuanhDay.setTen(data.getString("TENND"));
                float[] distance = new float[2];
                Location.distanceBetween(tamDuongTron.latitude,tamDuongTron.longitude,
                        data.getDouble("LAT"),data.getDouble("LON"),distance);
                banQuanhDay.setKhoangCach(distance[0]);
                if (banQuanhDay.getKhoangCach() < 5000f) {
                    dsBQD.add(banQuanhDay);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dsBQD;
    }
}
