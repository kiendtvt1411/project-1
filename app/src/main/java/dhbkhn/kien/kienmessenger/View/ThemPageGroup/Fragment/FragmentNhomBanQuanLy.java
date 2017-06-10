package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.AdapterPageNhom;
import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.ViewHolderPageNhom;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.PageDoanhNghiep;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 11/4/2016.
 */
public class FragmentNhomBanQuanLy extends Fragment {
    RecyclerView rvNhomPageThamGia;
    AdapterPageNhom adapter;
    DatabaseReference mDatabase;
    String nguoidung="";
    Button btnThemNhomMoi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_nhom_tham_gia_chung, container, false);
        rvNhomPageThamGia = (RecyclerView) row.findViewById(R.id.rvNhomPageThamGia);
        btnThemNhomMoi = (Button) row.findViewById(R.id.btnThemNhomMoi);
        btnThemNhomMoi.setVisibility(View.GONE);
        rvNhomPageThamGia.setLayoutManager(new LinearLayoutManager(getContext()));
        nguoidung = DangNhapMessenger.nguoidung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query ref = mDatabase.child("cong_ty_du_lich").child("thong_tin_so_luoc");
        adapter = new AdapterPageNhom(getContext(), PageDoanhNghiep.class,
                R.layout.custom_item_nhom_da_tham_gia, ViewHolderPageNhom.class, ref);
        rvNhomPageThamGia.setAdapter(adapter);
        return row;
    }
}
