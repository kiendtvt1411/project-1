package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.ChiDeShow;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/10/2016.
 */
public class FragmentShowLoTrinh extends Fragment {
    ImageView imgItemShowLoTrinh;
    TextView tvMoTaChiTiet, tvXemThem, tvTenDiaDiemTrongLoTrinh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_show_lo_trinh, container, false);
        Bundle bundle = getArguments();
        String keyDiaDiem = bundle.getString("keyDiaDiem");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        imgItemShowLoTrinh = (ImageView) row.findViewById(R.id.imgItemShowLoTrinh);
        tvMoTaChiTiet = (TextView) row.findViewById(R.id.tvMoTaChiTiet);
        tvTenDiaDiemTrongLoTrinh = (TextView) row.findViewById(R.id.tvTenDiaDiemTrongLoTrinh);
        tvXemThem = (TextView) row.findViewById(R.id.tvXemThem);

        ref.child("dia_diem").child("chi_de_hien_thi").child("van_hoa").child(keyDiaDiem).addListenerForSingleValueEvent(myListen);
        return row;
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }

    private ValueEventListener myListen = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ChiDeShow chiDeShow = dataSnapshot.getValue(ChiDeShow.class);
            String ten = chiDeShow.getTen_dd();
            tvTenDiaDiemTrongLoTrinh.setText(ten);
            String linkanh = chiDeShow.getHinh_dd();
            hienThiAnh(linkanh,imgItemShowLoTrinh);
            String mota = chiDeShow.getMota_dd();
            tvMoTaChiTiet.setText(mota);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.removeEventListener(myListen);
    }
}
