package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Fragment;

import android.content.Intent;
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

import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.AdapterPageNhom;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.TaoPageDoanhNghiep;

/**
 * Created by kiend on 11/4/2016.
 */
public class FragmentNhomBanThamGia extends Fragment {
    RecyclerView rvNhomPageThamGia;
    AdapterPageNhom adapter;
    Button btnThemNhomMoi;
    DatabaseReference mDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_nhom_tham_gia_chung, container, false);
        rvNhomPageThamGia = (RecyclerView) row.findViewById(R.id.rvNhomPageThamGia);
        btnThemNhomMoi = (Button) row.findViewById(R.id.btnThemNhomMoi);
        btnThemNhomMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThem = new Intent(getContext(), TaoPageDoanhNghiep.class);
                startActivity(iThem);
            }
        });
        rvNhomPageThamGia.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return row;
    }
}
