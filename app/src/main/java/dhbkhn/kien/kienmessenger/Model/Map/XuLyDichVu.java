package dhbkhn.kien.kienmessenger.Model.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dhbkhn.kien.kienmessenger.Model.KetNoiMang.ConnectInternet;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu.DichVu;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu.LoaiDichVu;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 10/9/2016.
 */
public class XuLyDichVu {
    public List<LoaiDichVu> layDanhSachLoaiDichVu(){
        List<LoaiDichVu>dsLdv = new ArrayList<>();
        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String,String>hmHam = new HashMap<>();
        hmHam.put("ham","LayDanhSachLoaiDichVu");
        attrs.add(hmHam);

        ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_NAME_URL, attrs);
        connectInternet.execute();

        try {
            String jsonData = connectInternet.get();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("DANHSACHLOAIDICHVU");
            int count = jsonArray.length();
            for(int i = 0;i<count;i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                LoaiDichVu loaiDichVu = new LoaiDichVu();
                loaiDichVu.setMALOAIDV(data.getInt("MALOAIDV"));
                loaiDichVu.setTENLOAIDV(data.getString("TENLOAIDV"));
                loaiDichVu.setICONDV(data.getString("ICONDV"));
                dsLdv.add(loaiDichVu);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dsLdv;
    }

    public List<DichVu> layDanhSachDichVuTheoMa(int maloaidv) {
        List<DichVu> dsDV = new ArrayList<>();
        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String,String> hmHam = new HashMap<>();
        hmHam.put("ham","LayDanhSachDichVuTheoMaLoaiDichVu");
        HashMap<String,String> hmMaLoai = new HashMap<>();
        hmMaLoai.put("maloaidv",String.valueOf(maloaidv));
        attrs.add(hmHam);
        attrs.add(hmMaLoai);

        ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_NAME_URL, attrs);
        connectInternet.execute();

        try {
            String jsonData = connectInternet.get();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("LOAIDICHVU");
            int count = jsonArray.length();
            for(int i = 0;i<count;i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                dsDV.add(parseJSONDichVu(data));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dsDV;
    }

    public DichVu layDichVuTheoMaDV(int madv){
        DichVu dichVu = new DichVu();
        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String,String>hmHam = new HashMap<>();
        hmHam.put("ham","LayDichVuTheoMaDV");
        HashMap<String,String>hmMaDV = new HashMap<>();
        hmMaDV.put("madv",String.valueOf(madv));

        attrs.add(hmHam);
        attrs.add(hmMaDV);

        ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_NAME_URL,attrs);
        connectInternet.execute();

        try {
            String jsonData = connectInternet.get();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("DICHVU");
            JSONObject data = jsonArray.getJSONObject(0);
            parseJSONDichVu(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dichVu;
    }

    public List<DichVu> layTatCaDichVu() {
        List<DichVu> dsDV = new ArrayList<>();
        List<HashMap<String,String>> attrs = new ArrayList<>();

        HashMap<String,String> hmHam = new HashMap<>();
        hmHam.put("ham","LayTatCaDichVu");
        attrs.add(hmHam);

        ConnectInternet connectInternet = new ConnectInternet(TrangChu.SERVER_NAME_URL, attrs);
        connectInternet.execute();

        try {
            String jsonData = connectInternet.get();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("TATCADICHVU");
            int count = jsonArray.length();
            for(int i = 0;i<count;i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                dsDV.add(parseJSONDichVu(data));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dsDV;
    }

    private DichVu parseJSONDichVu(JSONObject data) throws JSONException {
        DichVu dichVu = new DichVu();
        dichVu.setMADV(data.getInt("MADV"));
        dichVu.setTENDV(data.getString("TENDV"));
        dichVu.setDIACHIDV(data.getString("DIACHIDV"));
        dichVu.setLAT(data.getDouble("LAT"));
        dichVu.setLON(data.getDouble("LON"));
        dichVu.setHINHLONDV(data.getString("HINHLONDV"));
        dichVu.setHINHNHODV(data.getString("HINHNHODV"));
        dichVu.setLUOTKHACH(data.getInt("LUOTKHACH"));
        dichVu.setMALOAIDV(data.getInt("MALOAIDV"));
        dichVu.setICONDV(data.getString("ICONDV"));
        return dichVu;
    }
}
