package dhbkhn.kien.kienmessenger.Model.QuyenAdmin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dhbkhn.kien.kienmessenger.Model.KetNoiMang.ConnectInternet;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.CongTyDuLich;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 11/6/2016.
 */
public class XuLyThemCongTy {
    public String themCongTyMoi(CongTyDuLich ctdl){
        String ketqua = "";
        List<HashMap<String, String>> attrs = new ArrayList<>();

        try {
            String tencty = URLEncoder.encode(ctdl.getTen_cong_ty(), "UTF-8");
            String diachicty = URLEncoder.encode(ctdl.getDia_chi(), "UTF-8");
            String motacty = URLEncoder.encode(ctdl.getMo_ta(), "UTF-8");
            String giamdoccty = URLEncoder.encode(ctdl.getGiam_doc(), "UTF-8");
            String sdtcty = URLEncoder.encode(ctdl.getSdt(), "UTF-8");
            String webcty = URLEncoder.encode(ctdl.getWeb(), "UTF-8");
            HashMap<String, String> hmTenCt = new HashMap<>();
            hmTenCt.put("tenct", tencty);
            attrs.add(hmTenCt);
            HashMap<String, String> hmDiaChi = new HashMap<>();
            hmDiaChi.put("diachi", diachicty);
            attrs.add(hmDiaChi);
            HashMap<String, String> hmMoTa = new HashMap<>();
            hmMoTa.put("mota", motacty);
            attrs.add(hmMoTa);
            HashMap<String, String> hmGiamDoc = new HashMap<>();
            hmGiamDoc.put("giamdoc", giamdoccty);
            attrs.add(hmGiamDoc);
            HashMap<String, String> hmSDT = new HashMap<>();
            hmSDT.put("sdt", sdtcty);
            attrs.add(hmSDT);
            HashMap<String, String> hmWeb = new HashMap<>();
            hmWeb.put("web", webcty);
            attrs.add(hmWeb);

            ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_ASP_NAME_URL + "ThemCongTy", attrs);
            connectInternet.execute();

            String data = connectInternet.get();
            JSONObject jsonObject = new JSONObject(data);
            ketqua = jsonObject.getString("ketqua");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ketqua;
    }
}
