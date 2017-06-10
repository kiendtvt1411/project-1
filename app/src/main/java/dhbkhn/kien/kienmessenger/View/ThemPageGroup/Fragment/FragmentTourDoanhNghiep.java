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
import com.google.firebase.database.Query;

import dhbkhn.kien.kienmessenger.Adapter.Tour.AdapterTourDuLich;
import dhbkhn.kien.kienmessenger.Adapter.Tour.ViewHolderTour;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour.ThemTourMoi;

/**
 * Created by kiend on 11/3/2016.
 */
public class FragmentTourDoanhNghiep extends Fragment {
    Button btnThemTour;
    RecyclerView rvTour;
    String keyCongTy = "";
    String tenCongTy = "";
    AdapterTourDuLich adapterTourDuLich;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_tour_doanh_nghiep, container, false);
        rvTour = (RecyclerView) row.findViewById(R.id.rvTour);
        btnThemTour = (Button) row.findViewById(R.id.btnThemTour);
        keyCongTy = getActivity().getIntent().getStringExtra("keyCongTy");
        tenCongTy = getActivity().getIntent().getStringExtra("tencty");

        rvTour.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("tour").child(keyCongTy);
        adapterTourDuLich = new AdapterTourDuLich(getContext(), TourDuLich.class,
                R.layout.custom_item_tour_du_lich, ViewHolderTour.class, q, tenCongTy);
        rvTour.setAdapter(adapterTourDuLich);

        btnThemTour.setOnClickListener(myClick);
        return row;
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnThemTour) {
                Intent iThemTour = new Intent(getContext(), ThemTourMoi.class);
                iThemTour.putExtra("keyCongTy", keyCongTy);
                getActivity().startActivity(iThemTour);
            }
        }
    };
}
